package com.eddy.health.aide.service;

import com.eddy.health.aide.entity.HUserIndex;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eddy.health.aide.util.JsonResult;
import com.eddy.health.aide.vo.IndexDataVo;

import java.util.List;

/**
 * <p>
 * 用户表指标记录表 服务类
 * </p>
 *
 * @author Eddy Chen
 * @since2020-04
 */
public interface HUserIndexService extends IService<HUserIndex> {

    JsonResult createRecord(HUserIndex index);
}
