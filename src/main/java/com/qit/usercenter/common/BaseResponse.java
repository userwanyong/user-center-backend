package com.qit.usercenter.common;

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 基础响应类
 *
 * @author wenruohan
 */
@Data
public class BaseResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int status;

    private String message;

    private T data;

    public BaseResponse(int status, String msg, T data) {
        this.status = status;
        this.message = msg;
        this.data = data;
    }

    public BaseResponse(int status, String msg) {
        this.status = status;
        this.message = msg;
    }

}
