package com.qphone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by asus on 2019/5/29.
 */
@Controller

public class TestController {

   /* @RequestMapping("/first")
    public  String first(){
        return "index";
    }*/
   /*@RequestMapping("/{url1}/{url2}/{url}")
   public String show1(@PathVariable("url1") String url1, @PathVariable("url2") String url2, @PathVariable("url") String url) {
       return url1+"/"+url2+"/"+url;
   }
    @RequestMapping("/{url1}/{url2}")
    public String show2(@PathVariable("url1") String url1,@PathVariable("url2") String url2) {
        return url1+"/"+url2;
    }*/


    @RequestMapping("/{url}")
    public String show(@PathVariable("url") String url) {
        return url;
    }
}
