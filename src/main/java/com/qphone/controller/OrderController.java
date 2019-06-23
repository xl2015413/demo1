package com.qphone.controller;

import com.qphone.pojo.Order;
import com.qphone.pojo.Return;
import com.qphone.pojo.ZhongChou;
import com.qphone.service.OrderService;
import com.qphone.service.ReturnService;
import com.qphone.service.ZhongChouService;
import com.qphone.tools.OrdersTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 2019/6/3.
 */
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ZhongChouService zhongChouService;
    @Autowired
    private ReturnService returnService;

    @RequestMapping("/order")
    public String order(int id, int hid, Model model){
        ZhongChou zhongChou = zhongChouService.selectOne(id);
        zhongChou.getZcName();
        model.addAttribute("zhongChou",zhongChou);
        Return aReturn =returnService.selectOneReturn(hid);
        aReturn.getReturnMoney();
        model.addAttribute("aReturn",aReturn);
        Order order=new Order();
        order.setOrderId(OrdersTools.getOrderId(1));
        order.setMemberId(new BigDecimal(1));
        order.setZcId(zhongChou.getZcId());
        order.setOrderAmount(aReturn.getReturnMoney());
        order.setOrderTime(new Date());
        order.setOederNum(new BigDecimal(1));
        order.setOrderStatus("已关闭");
        orderService.insertOrders(order);
       // System.out.print(olist);
        return "success";
    }
    @RequestMapping("/myZhongChou")
    public String selectOrder(Model model){
        List<Order> olist = orderService.selectAllOrder(1);
        model.addAttribute("olist",olist);
        List<ZhongChou> zlist = zhongChouService.selectMy();
        model.addAttribute("zlist",zlist);
        return "minecrowdfunding";
    }

    @RequestMapping("/deleteOrder")
    public String deleteOrder(String id){
        System.out.print(id);
      orderService.deleteOrder(id);
        return "minecrowdfunding";
    }
}
