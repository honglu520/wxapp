package com.eddy.health.aide.controller;

import com.eddy.health.aide.entity.HUserIndex;
import com.eddy.health.aide.service.HUserIndexService;
import com.eddy.health.aide.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Eddy·Chen
 * @Email 835033913@qq.com
 * @Create 2019-05-11 10:25
 * 指标
 */
@RestController
@RequestMapping("/api")
public class IndexController {

    @Autowired
    private HUserIndexService indexService;

    @PostMapping("/record")
    public JsonResult recordIndex(@RequestBody @Validated HUserIndex index) {
        return indexService.createRecord(index);
    }

}
