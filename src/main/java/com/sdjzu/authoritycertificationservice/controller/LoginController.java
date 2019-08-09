package com.sdjzu.authoritycertificationservice.controller;
import com.sdjzu.authoritycertificationservice.util.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author :hly
 * @github :https://github.com/huangliangyun
 * @blog :blog.csdn.net/Sirius_hly
 * @date :2019/3/25
 */
@RestController
public class LoginController {

    //@RequestMapping将接收Get,Post,Head,Options等所有的请求方式
    /*@RequestMapping(value="/login_success",produces= {"application/json;charset=UTF-8"})
    public Result login(){
        Result result = new Result();
        result.setResult("登录成功");
        return result;
    }*/

   /* @RequestMapping(value="/login_error",  produces= {"application/json;charset=UTF-8"})
    public Result loginError(){
       Result result = new Result();
       result.setBusErrInfos("登录失败","用户名或密码错误!");
       return result;
    }*/

    @ResponseBody
    @RequestMapping(value="/login_page",produces= {"application/json;charset=UTF-8"})
    public Result loginPage() {
        Result result = new Result();
        result.setBusErrInfos("未登录","未登录");
        return result;
    }

    /*@RequestMapping(value="/logout",produces= {"application/json;charset=UTF-8"})
    public Result logout(HttpServletRequest request, HttpServletResponse response){
        Result result = new Result();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        result.setResult("注销成功!");
        return result;
    }*/

    @ResponseBody
    @RequestMapping(value="/admin",produces= {"application/json;charset=UTF-8"})
    public Result admin(){
        Result result = new Result();
        result.setResult("管理员!");
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/info",produces= {"application/json;charset=UTF-8"})
    public Result info(){
        Result result = new Result();
        result.setResult("个人信息!");
        return result;
    }
}


