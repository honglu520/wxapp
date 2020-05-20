package com.eddy.health.aide.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eddy.health.aide.constant.Const;
import com.eddy.health.aide.dao.HUserIndexMapper;
import com.eddy.health.aide.entity.HIllness;
import com.eddy.health.aide.entity.HUserIndex;
import com.eddy.health.aide.exception.TCException;
import com.eddy.health.aide.service.HIllnessService;
import com.eddy.health.aide.service.HUserIndexService;
import com.eddy.health.aide.util.EntityParamsAutoWrite;
import com.eddy.health.aide.util.JsonResult;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表指标记录表 服务实现类
 * </p>
 *
 * @author Eddy Chen
 * @since2020-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Log4j2
public class HUserIndexServiceImpl extends ServiceImpl<HUserIndexMapper, HUserIndex> implements HUserIndexService {

    @Autowired
    private HIllnessService illnessService;

    /**
     * 创建记录
     *
     * @param index
     * @return
     */
    @Override
    public JsonResult<HIllness> createRecord(HUserIndex index) {

        String illnessType = null;
        try {
            illnessType = computeIllnessType(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("预测疾病结果 : {}", illnessType);
        if (illnessType.equals("正常")) {
            return JsonResult.success(HIllness.builder().illnessName(illnessType).illnessInfo("您很健康继续保持").build());
        }
        
        switch (illnessType) {
        case "NXG":
            illnessType = "脑血管";
            break;
        case "XZB":
            illnessType = "心脏病";
            break;
        case "FB":
            illnessType = "肺病";
            break;
        case "TF":
            illnessType = "痛风";
            break;

    }

        HIllness illness = illnessService.getOne(new LambdaQueryWrapper<HIllness>().eq(HIllness::getIllnessName, illnessType.trim()));
        if (illness == null) {
            log.error("没有找到这种疾病类型 :{}", illnessType);
            return JsonResult.error("不存在的疾病类型");
        }
        index.setIllnessId(illness.getId());
        EntityParamsAutoWrite.addForPc(index);
        boolean state = this.save(index);
        return state ? JsonResult.success(illness) : JsonResult.actionFailure();
    }

    /**
     * 进行疾病计算  调用Py脚本
     *
     * @param index
     * @return
     */
    private String computeIllnessType(HUserIndex index) throws Exception {

        if (compluteIsNormal(index)) {
            return Const.Illness.NORMAL;
        }

        List<Double> data = Lists.newArrayList();
        data.add(index.getAnimalHeat());
        data.add(index.getBlood());
        data.add(index.getGlucose());
        data.add(index.getCholesterol());
        data.add(index.getPressureH());
        data.add(index.getPressureL());
        data.add(index.getUric());
        data.add(index.getTriglyceride());
        data.add(index.getUrinePh());
        String dataStr = data.toString();
        log.info("DataStrParams: ==> {}", dataStr);

        ProcessBuilder builder = new ProcessBuilder();
        List<String> list = new ArrayList<>();
        list.add("python");
        list.add(getPythonScriptFilePath("static/KNN_medical_predict.py"));
        list.add(dataStr);
        list.add(getDataFilePath());
        log.info("Python Script ===> {}", list);
        Process process = builder.command(list).start();
        //处理InputStream的线程
        InputStream inputStream = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        String str = br.readLine();

        new Thread() {
            @Override
            public void run() {
                BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line = null;

                try {
                    while ((line = err.readLine()) != null) {
                        log.error("ERROR == {}", line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        err.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        process.waitFor();

        org.apache.commons.io.IOUtils.closeQuietly(br);

        if (StringUtils.isBlank(str)) {
            throw new TCException("计算出错");
        }
        log.info("计算结果:{}", str);
        return str;
    }

    /**
     * 获得脚本文件
     *
     * @param pyName
     * @return
     */
    private String getPythonScriptFilePath(String pyName) {
        File root = null;
        try {
            root = ResourceUtils.getFile("classpath:" + pyName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (root.exists() == false) {
            throw new RuntimeException("没有找到脚本文件");
        }
        return root.getAbsolutePath();
    }

    /**
     * 获得训练文件
     *
     * @return
     */
    private String getDataFilePath() {
        File root = null;
        try {
            root = ResourceUtils.getFile("classpath:static/data2.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (root.exists() == false) {
            throw new RuntimeException("没有找到训练文件");
        }
        return root.getAbsolutePath();
    }

    /**
     * 判断是否是正常的
     *
     * @param index
     * @return
     */
    private boolean compluteIsNormal(HUserIndex index) {
        //判断体温
        if (!(index.getAnimalHeat() >= 36.5 && index.getAnimalHeat() <= 37)) {
            return false;
        }
        //判断血氧
        if (!(index.getBlood() >= 0.94 && index.getBlood() <= 1)) {
            return false;
        }
        //判断血糖  正常范围大约是3.9至6.9
        if (!(index.getGlucose() >= 3.9 && index.getGlucose() <= 6.9)) {
            return false;
        }
        //判断胆固醇  正常范围大约是2.84至5.17
        if (!(index.getCholesterol() >= 2.84 && index.getCholesterol() <= 5.17)) {
            return false;
        }
        //判断高压  正常范围大约是90至140
        if (!(index.getPressureH() >= 90 && index.getPressureH() <= 140)) {
            return false;
        }
        //判断低压  正常范围大约是60至90
        if (!(index.getPressureL() >= 60 && index.getPressureL() <= 90)) {
            return false;
        }
        //判断血尿酸 正常范围大约是237.9至356.9
        if (!(index.getUric() >= 237.9 && index.getUric() <= 356.9)) {
            return false;
        }
        //判断甘油三酯 正常范围大约是5至8
        if (!(index.getTriglyceride() >= 5 && index.getTriglyceride() <= 8)) {
            return false;
        }
        //判断尿液酸碱 正常范围大约是4.6至8.0
        if (!(index.getUrinePh() >= 4.6 && index.getUrinePh() <= 8.0)) {
            return false;
        }
        return true;
    }

}
