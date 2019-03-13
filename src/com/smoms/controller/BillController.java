package com.smoms.controller;

import com.alibaba.fastjson.JSON;
import com.smoms.pojo.Bill;
import com.smoms.pojo.Provider;
import com.smoms.pojo.User;
import com.smoms.service.BillService;
import com.smoms.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private ProviderService providerService;

    @RequestMapping("/allBills")
    public String showAllBills(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Bill> bills = billService.showAllBills();
        for (Bill bill : bills) {
            Integer providerId = bill.getProviderId();
            Provider provider = providerService.findProviderById(providerId);
            bill.setProviderName(provider.getProName());
        }
        List<Provider> providers = providerService.findAllProivders();
        session.setAttribute("providerList", providers);
        session.setAttribute("billList", bills);
        return "jsp/billlist";
    }

    @RequestMapping(value = "/getProviders", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String allProviders() {
        List<Provider> providerList = providerService.findAllProivders();
        String json = JSON.toJSONStringWithDateFormat(providerList, "yyyy-MM-dd-hh-mm-ss");
        return json;
    }

    @RequestMapping("/billAdd")
    public String addBill(HttpServletRequest request) {
        Bill bill = new Bill();
        String billCode = request.getParameter("billCode");
        String productName = request.getParameter("productName");
        String productUnit = request.getParameter("productUnit");
        String productCount = request.getParameter("productCount");
        String totalPrice = request.getParameter("totalPrice");
        String providerId = request.getParameter("providerId");
        String isPayment = request.getParameter("isPayment");
        String productDesc = request.getParameter("productDesc");
        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductDesc(productDesc);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount).setScale(2, BigDecimal.ROUND_DOWN));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2, BigDecimal.ROUND_DOWN));
        bill.setProviderId(Integer.parseInt(providerId));
        bill.setCreatedBy(((User) request.getSession().getAttribute("userSession")).getId());
        bill.setCreationDate(new Date());
        billService.addBill(bill);
        return "redirect:/bill/allBills.action";
    }

    @RequestMapping("/billView")
    public String billView(HttpServletRequest request) {
        String billId = request.getParameter("billId");
        Bill bill = billService.findBillById(Integer.parseInt(billId));
        Provider provider = providerService.findProviderById(bill.getProviderId());
        bill.setProviderName(provider.getProName());
        request.setAttribute("bill", bill);
        return "jsp/billview";
    }

    @RequestMapping("/billDetail")
    public String billDetail(HttpServletRequest request, HttpSession session) {
        String billId = request.getParameter("billId");
        Bill bill = billService.findBillById(Integer.parseInt(billId));
        Provider provider = providerService.findProviderById(bill.getProviderId());
        bill.setProviderName(provider.getProName());
        request.setAttribute("bill", bill);
        return "jsp/billmodify";
    }

    @RequestMapping("/billModify")
    public String BillModify(HttpServletRequest request, HttpSession session) {
        Bill bill = new Bill();
        String id = request.getParameter("id");
        String productName = request.getParameter("productName");
        String productDesc = request.getParameter("productDesc");
        String productUnit = request.getParameter("productUnit");
        String productCount = request.getParameter("productCount");
        String totalPrice = request.getParameter("totalPrice");
        String providerId = request.getParameter("providerId");
        String isPayment = request.getParameter("isPayment");
        bill.setId(Integer.parseInt(id));
        bill.setProductName(productName);
        bill.setProductDesc(productDesc);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount).setScale(2, BigDecimal.ROUND_DOWN));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2, BigDecimal.ROUND_DOWN));
        bill.setProviderId(Integer.parseInt(providerId));
        bill.setModifyBy(((User) request.getSession().getAttribute("userSession")).getId());
        bill.setModifyDate(new Date());
        billService.modifyBillById(bill);
        return "redirect:/bill/allBills.action";
    }

    @RequestMapping(value = "/billDel", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String removeBill(HttpServletRequest request) {
        String id = request.getParameter("billid");
        boolean flag = billService.reomveBillById(Integer.parseInt(id));
        String json = JSON.toJSONStringWithDateFormat(flag, "yyyy-MM-dd-hh-mm-ss");
        return json;
    }

    @RequestMapping("/findBillByTerm")
    public String findBillByTerm(HttpServletRequest request, HttpSession session) {
        String proName = request.getParameter("queryProductName");
        Integer ProviderId = Integer.parseInt(request.getParameter("queryProviderId"));
        Integer isPayment = Integer.parseInt(request.getParameter("queryIsPayment"));
        System.out.println(proName + ProviderId + isPayment);
        List<Bill> billList = billService.findBillByTerm(proName, ProviderId, isPayment);
        for (Bill bill : billList) {
            Integer providerId = bill.getProviderId();
            Provider provider = providerService.findProviderById(providerId);
            bill.setProviderName(provider.getProName());
        }
        session.setAttribute("billList", billList);
        return "jsp/billlist";
    }

}
