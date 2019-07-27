package com.aso.itsdf07.bean;

/**
 * @Description: 响应前端请求的基类
 * @Auther: itsdf07
 * @Date: 2019/7/26/026 18:55
 */
public class BaseResponseBean {
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应码
     */
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
