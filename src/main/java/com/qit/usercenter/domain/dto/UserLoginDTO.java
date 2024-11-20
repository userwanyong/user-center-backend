package com.qit.usercenter.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
/**
 * @author 永
 */
@Data
public class UserLoginDTO implements Serializable {

    private String username;

    private String password;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
