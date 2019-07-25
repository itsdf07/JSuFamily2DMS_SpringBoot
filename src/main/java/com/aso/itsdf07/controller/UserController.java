package com.aso.itsdf07.controller;

import com.aso.itsdf07.entity.UserEntity;
import com.aso.itsdf07.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
     * 跳转到用户登录页面:http://localhost:8080/user/login
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
     * @return
     */
    @RequestMapping(value = {"/userLogin"})
    private ModelAndView onUserLogin(UserEntity reqData) {
        ModelAndView modelAndView = new ModelAndView();
        String username = reqData.getUsername();
        String password = reqData.getPasswd();
        if (StringUtils.isEmpty(username)) {
            modelAndView.setViewName("loginHtml");
            modelAndView.addObject("msg", "账号不能为空");
            return modelAndView;
        }

        if (StringUtils.isEmpty(password)) {
            modelAndView.setViewName("loginHtml");
            modelAndView.addObject("msg", "密码不能为空");
            return modelAndView;
        }

        UserEntity user = userService.onUserLogin(username, password);

        if (user != null) {
            modelAndView.setViewName("mainHtml");
            modelAndView.addObject("msg", "登陆成功");//登录成功
//            request.getSession().setAttribute("session_user", user);     //将用户信息放入session  用于后续的拦截器
            return modelAndView;
        }
        modelAndView.setViewName("loginHtml");
        modelAndView.addObject("msg", "登录失败，用户名或密码错误");
        return modelAndView;
    }
}
