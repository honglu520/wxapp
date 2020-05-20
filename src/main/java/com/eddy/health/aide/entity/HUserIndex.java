package com.eddy.health.aide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 用户表指标记录表
 * </p>
 *
 * @author Eddy Chen
 * @since2020-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HUserIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 体温
     */
    @NotNull(message = "请输入体温")
    @DecimalMin(value = "35", message = "体温最小测量值为35")
    @DecimalMax(value = "42", message = "体温最大测量值为42")
    private Double animalHeat;

    /**
     * 血氧
     */
    @NotNull(message = "请输入血氧")
    @DecimalMax(value = "0.99", message = "血氧最大测量值为0.99")
    private Double blood;

    /**
     * 血糖
     */
    @NotNull(message = "请输入血糖")
    private Double glucose;

    /**
     * 胆固醇
     */
    @NotNull(message = "请输入胆固醇")
    private Double cholesterol;

    /**
     * 血压
     */
    @NotNull(message = "请输入血压 高压")
    private Double pressureH;

    @NotNull(message = "请输入血压 低压")
    private Double pressureL;

    /**
     * 血尿酸
     */
    @NotNull(message = "请输入血尿酸")
    private Double uric;

    /**
     * 甘油三酯
     */
    @NotNull(message = "请输入甘油三酯")
    private Double triglyceride;

    /**
     * 尿液酸碱度
     */
    @NotNull(message = "请输入尿液酸碱度")
    private Double urinePh;

    /**
     * 疾病类型编号
     */
    private Integer illnessId;

    /**
     * 结果值
     */
    private Double val;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建端
     */
    private String createForm;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建Function名
     */
    private String createMethod;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新端
     */
    private String updateForm;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新Function名
     */
    private String updateMethod;

    /**
     * 是否弃用：1.正常，0.失效
     */
    private Integer loseFlag;


}
