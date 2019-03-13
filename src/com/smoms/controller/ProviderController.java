package com.smoms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.smoms.pojo.Bill;
import com.smoms.pojo.Provider;
import com.smoms.pojo.User;
import com.smoms.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    @RequestMapping("/getProviders")
    @ResponseBody
    public String getProviders() {
        List<Provider> providerList = providerService.findAllProivders();
        System.out.println();
        String json = JSON.toJSONStringWithDateFormat(providerList, "yyyy-MM-dd hh-mm-ss");
        return json;
    }

    @RequestMapping("/allProvider")
    public String allProviders(HttpSession session) {
        List<Provider> providers = providerService.findAllProivders();
        session.setAttribute("providerList", providers);
        return "jsp/providerlist";
    }

    @RequestMapping("/addProvider")
    public String addProvider(HttpServletRequest request) {
        String proCode = request.getParameter("proCode");
        String proName = request.getParameter("proName");
        String proDesc = request.getParameter("proDesc");
        String proContact = request.getParameter("proContact");
        String proPhone = request.getParameter("proPhone");
        String proAddress = request.getParameter("proAddress");
        String proFax = request.getParameter("proFax");
        Provider provider = new Provider();
        provider.setProCode(proCode);
        provider.setProName(proName);
        provider.setProDesc(proDesc);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProAddress(proAddress);
        provider.setProFax(proFax);
        provider.setCreatedBy(((User) request.getSession().getAttribute("userSession")).getId());
        provider.setCreationDate(new Date());
        providerService.addProvider(provider);
        return "redirect:/provider/allProvider.action";
    }

    @RequestMapping("/findBillByTerm")
    public String findProviderByTerm(HttpServletRequest request, HttpSession session) {
        String proCode = request.getParameter("queryProCode");
        String proName = request.getParameter("queryProName");
        List<Provider> providerList = providerService.findProviderByTerm(proCode, proName);
        session.setAttribute("providerList", providerList);
        return "jsp/providerlist";
    }

    @RequestMapping(value = "/providerDel", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String providerDel(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("proid"));
        boolean flag = providerService.reomveProviderById(id);
        String json = JSON.toJSONStringWithDateFormat(flag, "yyyy-MM-dd-hh-mm-ss");
        return json;
    }

    @RequestMapping("/providerView")
    public String providerView(HttpServletRequest request, HttpSession session) {
        Integer proid = Integer.parseInt(request.getParameter("proid"));
        Provider provider = providerService.findProviderById(proid);
        request.setAttribute("provider", provider);
        return "jsp/providerview";
    }

    @RequestMapping("/providerModify")
    public String provideModify(HttpServletRequest request, HttpSession session) {
        Integer proid = Integer.parseInt(request.getParameter("proid"));
        Provider provider = providerService.findProviderById(proid);
        session.setAttribute("provider", provider);
        return "jsp/providermodify";
    }

    @RequestMapping("/providerChange")
    public String providerChange(HttpServletRequest request,HttpSession session) {
        Provider provider = (Provider) session.getAttribute("provider");
        int proId = provider.getId();
        String proCode = request.getParameter("proCode");
        String proName = request.getParameter("proName");
        String proContact = request.getParameter("proContact");
        String proPhone = request.getParameter("proPhone");
        String proAddress = request.getParameter("proAddress");
        String proFax = request.getParameter("proFax");
        String proDesc = request.getParameter("proDesc");
        Provider pro=new Provider();
        pro.setProCode(proCode);
        pro.setProName(proName);
        pro.setProContact(proContact);
        pro.setProPhone(proPhone);
        pro.setProAddress(proAddress);
        pro.setProFax(proFax);
        pro.setProDesc(proDesc);
        pro.setModifyBy(((User)request.getSession().getAttribute("userSession")).getId());
        pro.setModifyDate(new Date());
        providerService.modifyProviderById(pro,proId);
        return "redirect:/provider/allProvider.action";
    }

}
