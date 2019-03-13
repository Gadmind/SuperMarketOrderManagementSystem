package com.smoms.controller;

import com.alibaba.fastjson.JSON;
import com.smoms.pojo.Provider;
import com.smoms.pojo.Role;
import com.smoms.pojo.User;
import com.smoms.service.RoleService;
import com.smoms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/allUsers")
    public String userList(HttpSession session) {
        List<User> users = userService.findAllUser();
        List<Role> roles = roleService.allRoles();
        for (User user : users) {
            for (Role role : roles) {
                if (user.getUserRole().equals(role.getId())) {
                    user.setUserRoleName(role.getRoleName());
                }
            }
        }
        session.setAttribute("roleList", roles);
        session.setAttribute("userList", users);
        return "jsp/userlist";
    }

    @RequestMapping(value = "/getRoleList", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getRoleList(HttpSession session) {
        List<Role> roleList = (List<Role>) session.getAttribute("roleList");
        String json = JSON.toJSONStringWithDateFormat(roleList, "yyyy-MM-dd-hh-mm-ss");
        return json;
    }

    @RequestMapping(value = "/opChange", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String opChange(HttpSession session, String oldPassword) {
        User user = (User) session.getAttribute("userSession");
        String result;
        if (oldPassword == null && oldPassword == "") {
            result = "error";
        } else if (user.getUserPassword().equals(oldPassword)) {
            result = "true";
        } else {
            result = "false";
        }
        String json = JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd-hh-mm-ss");
        return json;
    }

    @RequestMapping("/pwdChange")
    public String pwdChange(HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("userSession");
        String newPassword = request.getParameter("rnewpassword");
        userService.changeUpwdById(newPassword, user.getId());
        session.removeAttribute("userSession");
        return "redirect:/login.jsp";
    }

    @RequestMapping("/findUserByTerm")
    public String findUserByTrem(HttpServletRequest request, HttpSession session) {
        String userName = request.getParameter("queryname");
        Integer roleId = Integer.parseInt(request.getParameter("queryUserRole"));
        List<User> users = userService.findUserByTerm(userName, roleId);
        List<Role> roles = roleService.allRoles();
        for (User user : users) {
            for (Role role : roles) {
                if (user.getUserRole().equals(role.getId())) {
                    user.setUserRoleName(role.getRoleName());
                }
            }
        }
        session.setAttribute("userList", users);
        return "jsp/userlist";
    }

    @RequestMapping(value = "UCExist", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String UCExist(String userCode) {
        User user = userService.findUserByUserCode(userCode);
        String result;
        if (user != null) {
            result = "exist";
        } else {
            result = "null";
        }
        String json = JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd-hh-mm-ss");
        return json;
    }

    @RequestMapping("/userAdd")
    public String userAdd(HttpServletRequest request) {
        String userCode = request.getParameter("userCode");
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("ruserPassword");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String userRole = request.getParameter("userRole");
        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setGender(Integer.parseInt(gender));
        try {
            Date data = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            user.setBirthday(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.parseInt(userRole));
        user.setCreatedBy(((User) request.getSession().getAttribute("userSession")).getId());
        user.setCreationDate(new Date());
        userService.addUser(user);
        return "redirect:/user/allUsers.action";
    }

    @RequestMapping("/userView")
    public String userView(HttpSession session, String uid) {
        User user = userService.findUserById(Integer.parseInt(uid));
        Role role = roleService.findRoleById(user.getUserRole());
        user.setUserRoleName(role.getRoleName());
        session.setAttribute("user", user);
        return "jsp/userview";
    }

    @RequestMapping("/userModify")
    public String userModify(String uid, HttpSession session) {
        User user = userService.findUserById(Integer.parseInt(uid));
        session.setAttribute("user", user);
        return "jsp/usermodify";
    }

    @RequestMapping("/userChange")
    public String userChange(HttpServletRequest request) {
        Integer uid=Integer.parseInt(request.getParameter("uid"));
        String userName = request.getParameter("userName");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String userRole = request.getParameter("userRole");
        User user = new User();
        user.setUserName(userName);
        user.setGender(Integer.parseInt(gender));
        try {
            Date data = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            user.setBirthday(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.parseInt(userRole));
        user.setModifyBy(((User) request.getSession().getAttribute("userSession")).getId());
        user.setModifyDate(new Date());
        userService.modifyUserById(user,uid);
        return "redirect:/user/allUsers.action";
    }
    @RequestMapping(value = "/userDel", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String removeUser(HttpServletRequest request) {
        Integer uid= Integer.parseInt(request.getParameter("uid"));
        boolean flag = userService.reomveUserById(uid);
        String json = JSON.toJSONStringWithDateFormat(flag, "yyyy-MM-dd-hh-mm-ss");
        return json;
    }
}
