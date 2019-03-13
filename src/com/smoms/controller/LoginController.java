package com.smoms.controller;

import com.smoms.pojo.User;
import com.smoms.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpSession session) {
        String userCode = request.getParameter("userCode");
        String userPassword = request.getParameter("userPassword");
        User user = userService.findUserByCP(userCode, userPassword);
        if (user == null) {
            request.setAttribute("error","用户名或密码错误");
            return "forward:login.jsp";
        } else {
            session.setAttribute("userSession", user);
            return "jsp/frame";
        }
    }

    @RequestMapping("/loginOut")
    public String longOut(HttpSession session) {
        session.removeAttribute("userSession");
        return "redirect:/login.jsp";
    }
}
