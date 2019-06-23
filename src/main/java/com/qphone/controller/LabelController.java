package com.qphone.controller;

import com.google.gson.Gson;
import com.qphone.pojo.Label;
import com.qphone.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by asus on 2019/6/4.
 */
@Controller
public class LabelController {
    @Autowired
    private LabelService labelService;
    @RequestMapping("/select")
    public String selectAll(int id, Model model){


        List<Label> list = labelService.selectAllLabel(id);

        System.out.print(list);
        model.addAttribute("list",list);
        return "start-step-1::load";
    }
}
