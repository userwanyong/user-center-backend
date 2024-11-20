package com.qit.usercenter.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @author 永
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性别 0-男 1-女
     */
    private Integer gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否启用 0-启用 1-禁用
     */
    private Integer status;

    /**
     * 角色 0-管理员 1-用户
     */
    private Integer role;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}