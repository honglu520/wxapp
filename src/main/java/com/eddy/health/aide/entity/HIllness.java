package com.eddy.health.aide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 疾病类型表
 * </p>
 *
 * @author Eddy Chen
 * @since2020-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
public class HIllness implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 疾病类型
     */
    private String illnessName;

    /**
     * 疾病值
     */
    private Double illnessVal;

    /**
     * 疾病建议
     */
    private String illnessInfo;

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
