package com.smoms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 杜先森
 * 页面转跳控制
 */
@Controller
@RequestMapping("/PJC")
public class PageJumpController {
    /**
     * 跳转到修改密码页面
     *
     * @return
     */
    @RequestMapping("/pwdmodity")
    public String pwdmodity() {
        return "jsp/pwdmodify";
    }

    /**
     * 跳转到订单添加页面
     * @return
     */
    @RequestMapping("/billAdd")
    public String billAdd() {
        return "jsp/billadd";
    }

    /**
     * 跳转到用户添加页面
     * @return
     */
    @RequestMapping("/userAdd")
    public String userAdd() {
        return "jsp/useradd";
    }
    @RequestMapping("/providerAdd")
    public String providerAdd() {
        return "jsp/provideradd";
    }

}
