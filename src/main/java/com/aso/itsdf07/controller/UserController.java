package com.aso.itsdf07.controller;

import com.aso.itsdf07.entity.UserEntity;
import com.aso.itsdf07.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: TODO
 * @Auther: itsdf07
 * @Date: 2019/7/23/023 14:29
 */
@Controller
@RequestMapping(value = {"/user"})
public class UserController {
    /**
     * 注入service
     */
    @Autowired
    private UserService userService;

    /**
     * 跳转到用户登录页面
     *
     * @return 登录页面
     */
    @RequestMapping(value = {"/login"})
    public String loginHtml() {
        return "loginHtml";
    }

    /**
     * 获取用户名与密码，用户登录
     *
     * @param request
     * @param username
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/userLogin"})
    private String onUserLogin(HttpServletRequest request,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password) {
        if (StringUtils.isEmpty(username)) {
            return "用户名不能为空";
        }

        if (StringUtils.isEmpty(password)) {
            return "密码不能为空";
        }

        UserEntity user = userService.onUserLogin(username, password);

        if (user != null) {                                                  //登录成功
            request.getSession().setAttribute("session_user", user);     //将用户信息放入session  用于后续的拦截器
            return "登录成功";
        }
        return "登录失败，用户名或密码错误";
    }
}
