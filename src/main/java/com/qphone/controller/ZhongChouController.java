package com.qphone.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qphone.pojo.Return;
import com.qphone.pojo.ZhongChou;
import com.qphone.service.ReturnService;
import com.qphone.service.ZhongChouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sun.tools.tree.SynchronizedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 2019/5/30.
 */
@Controller
public class ZhongChouController {
    @Autowired
    private ZhongChouService zhongChouService;
    @Autowired
    private ReturnService returnService;
    @RequestMapping("/index")
    public String selectAll(Model model){
        List<ZhongChou> klist= zhongChouService.selectAllBySort("科技");

        model.addAttribute("klist",klist);
        List<ZhongChou> slist= zhongChouService.selectAllBySort("设计");
        model.addAttribute("slist",slist);
        List<ZhongChou> nlist= zhongChouService.selectAllBySort("农业");
        model.addAttribute("nlist",nlist);
        List<ZhongChou> qlist= zhongChouService.selectAllBySort("其它");
        model.addAttribute("qlist",qlist);

        return  "index";
    }
    @RequestMapping("/showone")
    public String selectOne(int id, Model model){
        ZhongChou zc = zhongChouService.selectOne(id);
        model.addAttribute("zc",zc);
         List<Return> rlist = returnService.selectAllReturn(id);
         model.addAttribute("rlist",rlist);
        return "project";
    }
    @RequestMapping("/showall")
    public String selectAll(@RequestParam(value="pages",defaultValue ="1")int pages, int id,Model model){
        PageHelper.startPage(pages,4);
           List<ZhongChou> list = zhongChouService.selectAll(id);
           PageInfo<ZhongChou> page=new PageInfo<>(list);
           model.addAttribute("page",page);
        return "projects";
    }
    @RequestMapping("/projects")
    public String selectALL(@RequestParam(value="pages",defaultValue ="1")int pages, Model model){
        PageHelper.startPage(pages,4);
        List<ZhongChou> list =zhongChouService.selectAll();
        System.out.print(list);
        PageInfo<ZhongChou> page=new PageInfo<>(list);
        model.addAttribute("page",page);
        return "projects";
    }
   @RequestMapping("/pay")
    public String pay(int hid,int id,Model model) {
       Return aReturn = returnService.selectOneReturn(hid);
       model.addAttribute("aReturn",aReturn);
       ZhongChou zc = zhongChouService.selectOne(id);
       model.addAttribute("zc",zc);
       return "pay-step-1";
   }
   @RequestMapping(value="/faqi",method= RequestMethod.POST)
    public String faqi(@RequestParam("file") MultipartFile file, ZhongChou zc, HttpServletRequest request){


       String filename = file.getOriginalFilename();
       String realPath = request.getRealPath("/static/img/");

       System.out.println(realPath+"----------->");

       File file1=new File(realPath);
       if(!file1.exists()){
           file1.mkdirs();
       }
       File file2=new File(file1+"/"+filename);
       try {
           file2.createNewFile();
           file.transferTo(file2);//user.getPhoto().transferTo(**)  <img src="数据库中的地址" />
       } catch (IOException e) {
           e.printStackTrace();
       }

       System.out.println("上传成功！");
       zc.setZcHeader("/static/img/"+filename );
       HttpSession session = request.getSession();
       session.setAttribute("zc",zc);

       return "start-step-2";
   }
   private  List<Return> list=new ArrayList<>();
   @RequestMapping(value="/huibao",method=RequestMethod.POST)
    public String huibao(Return aReturn,HttpServletRequest request,Model model){
        list.add(aReturn);
       model.addAttribute("list", list);
       HttpSession session = request.getSession();
       session.setAttribute("list",list);

       return "start-step-2";

   }
   @RequestMapping(value="submit",method=RequestMethod.POST)
    public String submit(ZhongChou zc,HttpServletRequest request){
        zc.getZcIdentity();
        zc.getZcAccount();
        System.out.print(zc.getZcHeader());

       HttpSession session=request.getSession();
       Object zc1 = session.getAttribute("zc");
       if(zc1!=null){
           ZhongChou zc2=(ZhongChou)zc1;

            ZhongChou zhongchou=new ZhongChou();
           zhongchou.setSortId(zc2.getSortId());
           zhongchou.setZcName(zc2.getZcName());
           zhongchou.setZcTarget(zc2.getZcTarget());
           zhongchou.setZcCycle(zc2.getZcCycle());
           zhongchou.setZcNumber(zc2.getZcNumber());
           zhongchou.setZcCreatetime(new Date());
           zhongchou.setZcDescription(zc2.getZcDescription());
           zhongchou.setZcPresentation(zc2.getZcPresentation());
           zhongchou.setZcIntroduction(zc2.getZcIntroduction());
           zhongchou.setZcPhone(zc2.getZcPhone());
           zhongchou.setZcCustomer(zc2.getZcCustomer());
           zhongchou.setZcAccount(zc.getZcAccount());
           zhongchou.setZcIdentity(zc.getZcIdentity());
           zhongchou.setZcHeader(zc2.getZcHeader());
           zhongChouService.insertOne(zhongchou);
           ZhongChou zhongChou = zhongChouService.selectId(zc2.getZcName());
            zhongchou.getZcId();
           Object list = session.getAttribute("list");
           if(list!=null){
               List<Return> list1=(List<Return>)list;
               for(Return aReturn:list1){
                   aReturn.setZcId(zhongchou.getZcId());
                   returnService.insertReturn(aReturn);
               }
           }

       }



       return "start-step-4";
   }
}
