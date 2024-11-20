package com.qit.usercenter.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
/**
 * @author 永
 */
@Data
public class UserAddDTO implements Serializable {

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

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
