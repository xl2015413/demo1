package com.qphone.controller;

import com.qphone.pojo.Addressr;
import com.qphone.pojo.NewAddress;
import com.qphone.pojo.Return;
import com.qphone.pojo.ZhongChou;
import com.qphone.service.AddressService;
import com.qphone.service.ReturnService;
import com.qphone.service.ZhongChouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by asus on 2019/6/3.
 */
@Controller
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private ZhongChouService zhongChouService;
    @Autowired
    private ReturnService returnService;
    @RequestMapping("/fukuan")
    public String selectAllAddress(int hid, int id, Model model){

        ZhongChou zc = zhongChouService.selectOne(id);
        model.addAttribute("zc",zc);
        List<Addressr> alist = addressService.selectAllAddress("liudehua");
        model.addAttribute("alist",alist);

        Return aReturn = returnService.selectOneReturn(hid);
        model.addAttribute("aReturn",aReturn);

        return "pay-step-2";
    }
    @RequestMapping(value="/newaddress",method = RequestMethod.POST)
    public String address(NewAddress na){
        System.out.println(na);
        Addressr address=new Addressr();

        address.setAddressName(na.getName());
        address.setAddressPhone(na.getPhone());
        address.setAddressInfo(na.getAddress());

        addressService.insertAddress(address);

        return "pay-step-2";
    }
    @RequestMapping("/orders")
    public String orders(int id,int hid){

        ZhongChou zc = zhongChouService.selectOne(id);

        return "pay-step-2";
    }
}
