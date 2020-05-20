package com.eddy.health.aide.dao;

import com.eddy.health.aide.entity.HUserIndex;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eddy.health.aide.vo.IndexDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表指标记录表 Mapper 接口
 * </p>
 *
 * @author Eddy Chen
 * @since2020-04
 */
public interface HUserIndexMapper extends BaseMapper<HUserIndex> {

    /**
     * 获取历史数据
     *
     * @param startTime 筛选条件 开始时间
     * @param endTime   筛选条件结束时间
     * @param userId
     * @param indexId
     * @return
     */
    List<IndexDataVo> selectDataList(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userId") Integer userId, @Param("indexId") Integer indexId);

    /**
     * 获取某一条记录
     *
     * @param time
     * @param userId
     * @return
     */
    IndexDataVo getDataForDay(@Param("time") String time, @Param("userId") Integer userId);

}
