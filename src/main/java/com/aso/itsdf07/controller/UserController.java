package com.aso.itsdf07.controller;

import com.aso.itsdf07.bean.BaseResponseBean;
import com.aso.itsdf07.bean.RequestUserLoginEntity;
import com.aso.itsdf07.entity.UserEntity;
import com.aso.itsdf07.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Auther: itsdf07
 * @Date: 2019/7/23/023 14:29
 */
@Controller
@RequestMapping(value = {"/user"})
public class UserController extends BaseController {
    /**
     * 注入service
     */
    @Autowired
    private UserService userService;

    /**
     * 获取用户名与密码，用户登录
     *
     * @return
     */
    @RequestMapping(value = {"/userLogin"})
    @ResponseBody
    private ModelAndView onUserLogin(UserEntity reqData) {
        ModelAndView modelAndView = new ModelAndView();
        String username = reqData.getUsername();
        String password = reqData.getPasswd();
        if (StringUtils.isEmpty(username)) {
            modelAndView.setViewName("error");
            modelAndView.addObject("msg", "账号不能为空");
            return modelAndView;
        }

        if (StringUtils.isEmpty(password)) {
            modelAndView.setViewName("error");
            modelAndView.addObject("msg", "密码不能为空");
            return modelAndView;
        }

        UserEntity user = userService.onUserLogin(username, password);

        if (user == null) {
            modelAndView.setViewName("error");
            modelAndView.addObject("msg", "用户不存在");
//            request.getSession().setAttribute("session_user", user);     //将用户信息放入session  用于后续的拦截器
            return modelAndView;
        }
        modelAndView.setViewName("mainHtml");
        modelAndView.addObject("msg", "登陆成功");//登录成功
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    /**
     * 获取用户名与密码，用户登录
     *
     * @return
     */
    @RequestMapping(value = {"/getAllUsers"})
    @ResponseBody
    private Object getAllUsers(String username) {
        Map<String, Object> usersMap = new HashMap<>();
        System.out.println("username::" + username);
        logger.info("username:::::::" + username);

        List<UserEntity> users = userService.getAllUsers();

        if (users.isEmpty()) {
            usersMap.put("msg", "没有用户");
            return usersMap;
        }
        usersMap.put("msg", "用户列表获取成功");
        usersMap.put("users", users);
        return usersMap;
    }


    /**
     * 用户登录
     *
     * @return
     */
    @RequestMapping(value = "/user2Login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    private String onUserLogin(@RequestParam(value = "data") String request2Json) {
        RequestUserLoginEntity userLoginEntity = new Gson().fromJson(request2Json, RequestUserLoginEntity.class);
        String username = userLoginEntity.getUsername();
        String password = userLoginEntity.getPasswd();
        BaseResponseBean responseBean = new BaseResponseBean();
        if (StringUtils.isEmpty(username)) {
            responseBean.setMessage("账号不能为空");
            responseBean.setCode(500);
            return new Gson().toJson(responseBean);
        }

        if (StringUtils.isEmpty(password)) {
            responseBean.setMessage("密码不能为空");
            responseBean.setCode(500);
            return new Gson().toJson(responseBean);
        }

        UserEntity user = userService.onUserLogin(username, password);

        if (user == null) {
            responseBean.setMessage("用户不存在");
            responseBean.setCode(404);
            return new Gson().toJson(responseBean);
        }
        responseBean.setMessage("登录成功");
        responseBean.setCode(200);
        return new Gson().toJson(responseBean);
    }
}
