package com.itheima.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: yp
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @PreAuthorize("hasAuthority('add')")
    @RequestMapping("/fun01")
    public Map fun01(){
        System.out.println("fun01执行了...");
        Map map = new HashMap();
        map.put("msg","fun01");
        return map;
    }

    @PreAuthorize("hasAuthority('update')")
    @RequestMapping("/fun02")
    public Map fun02(){
        System.out.println("fun02执行了...");
        Map map = new HashMap();
        map.put("msg","fun02");
        return map;
    }

}
