package com.aso.itsdf07.bean;

/**
 * @Description: 用户登录请求数据
 * @Auther: itsdf07
 * @Date: 2019/7/27/027 14:26
 */
public class RequestUserLoginEntity extends BaseRequestBean {
    private String username;
    private String passwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
