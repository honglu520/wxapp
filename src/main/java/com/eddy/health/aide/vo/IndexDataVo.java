package com.eddy.health.aide.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Eddy·Chen
 * @Email 835033913@qq.com
 * @Create 2019-05-11 11:24
 */
@Data
public class IndexDataVo implements Serializable {

    /**
     * 体温
     */
    private Double animalHeat;

    /**
     * 血氧
     */
    private Double blood;

    /**
     * 血糖
     */
    private Double glucose;

    /**
     * 胆固醇
     */
    private Double cholesterol;

    /**
     * 血压
     */
    private Double pressureH;


    private Double pressureL;

    /**
     * 血尿酸
     */
    private Double uric;

    /**
     * 甘油三酯
     */
    private Double triglyceride;

    /**
     * 尿液酸碱度
     */
    private Double urinePh;

    /**
     * 疾病类型
     */
    private String illnessName;

    /**
     * 疾病建议
     */
    private String illnessInfo;


    private Date createTime;
}
