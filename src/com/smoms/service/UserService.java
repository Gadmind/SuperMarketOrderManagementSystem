package com.smoms.service;

import com.smoms.pojo.User;

import java.util.List;

public interface UserService {
    List<User> findAllUser();

    User findUserByCP(String userCode, String userPassword);

    int changeUpwdById(String userPassword, int id);

    List<User> findUserByTerm(String userName, int roleId);

    User findUserByUserCode(String userCode);

    int addUser(User user);

    User findUserById(int id);

    int modifyUserById(User user, int uid);

    boolean reomveUserById(int uid);
}
