package com.qit.usercenter.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
/**
 * @author 永
 */
@Data
public class UserVo implements Serializable {

    private String id;

    private String username;

    private String password;

    private String nickname;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private Integer gender;

    private String phone;

    private String email;

    private Integer status;

    private Integer role;

    @JsonProperty("created_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @JsonProperty("updated_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
