package com.aso.itsdf07.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 用来实现界面重定向跳转
 * @Auther: itsdf07
 * @Date: 2019/7/27/027 15:19
 */
@Controller
@RequestMapping(value = {"/redirect"})
public class Redirect2HtmlController extends BaseController {
    /**
     * 跳转到用户登录页面:http://localhost:8080/redirect/demoHtml
     *
     * @return 登录页面
     */
    @RequestMapping(value = {"/demoHtml"})
    public String demoHtml() {
        return "demoHtml";
    }

    /**
     * 跳转到用户登录页面:http://localhost:8080/redirect/loginHtml
     *
     * @return 登录页面
     */
    @RequestMapping(value = {"/loginHtml"})
    public String loginHtml() {
        return "loginHtml";
    }

    /**
     * 跳转到用户选择功能页面:http://localhost:8080/redirect/chooseHtml
     *
     * @return 功能选择界面
     */
    @RequestMapping(value = {"/chooseHtml"})
    public String chooseHtml() {
        return "chooseHtml";
    }

    /**
     * 跳转到用户登录页面:http://localhost:8080/redirect/mainHtml
     *
     * @return 进入主页
     */
    @RequestMapping(value = {"/mainHtml"})
    public String mainHtml() {
        return "mainHtml";
    }
    /**
     * 跳转到用户登录页面:http://localhost:8080/redirect/transHtml
     *
     * @return
     */
    @RequestMapping(value = {"/transHtml"})
    public String transHtml() {
        return "transHtml";
    }

}
