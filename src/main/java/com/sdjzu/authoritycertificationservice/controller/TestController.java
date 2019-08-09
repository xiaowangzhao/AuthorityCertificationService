package com.sdjzu.authoritycertificationservice.controller;

import com.sdjzu.authoritycertificationservice.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author :hly
 * @github :https://github.com/huangliangyun
 * @blog :http://www.javahly.com/
 * @CSDN :blog.csdn.net/Sirius_hly
 * @date :2019/4/12
 */
@RestController
public class TestController {


    @RequestMapping(value = "/test")
    public Result getNameAndClass(HttpServletRequest request){

        Result result = new Result();
        String username = (String) request.getSession().getAttribute("username");
        System.err.println("session："+request.getSession());
        System.err.println("username："+username);
        result.setResult(username);
        //Map map = leaveService.getNameAndClassById(s_id);
        //result.setResult(map);
        return result;
    }
}