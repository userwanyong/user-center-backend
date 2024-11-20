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
public class UserRegisterDTO implements Serializable {

    private String username;

    private String password;

    @JsonProperty("check_password")
    private String checkPassword;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
