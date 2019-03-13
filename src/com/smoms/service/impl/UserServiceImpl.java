package com.smoms.service.impl;

import com.smoms.mapper.UserMapper;
import com.smoms.pojo.User;
import com.smoms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAllUser() {
        return userMapper.selAllUser();
    }

    @Override
    public User findUserByCP(String userCode, String userPassword) {
        return userMapper.selUserByCP(userCode, userPassword);
    }

    @Override
    public int changeUpwdById(String userPassword, int id) {
        return userMapper.updUpwdById(userPassword, id);
    }

    @Override
    public List<User> findUserByTerm(String userName, int roleId) {
        return userMapper.selUserByTerm(userName, roleId);
    }

    @Override
    public User findUserByUserCode(String userCode) {
        return userMapper.selUserByUserCode(userCode);
    }

    @Override
    public int addUser(User user) {
        return userMapper.insUser(user);
    }

    @Override
    public User findUserById(int id) {
        return userMapper.selUserById(id);
    }

    @Override
    public int modifyUserById(User user, int uid) {
        return userMapper.updUserById(user,uid);
    }

    @Override
    public boolean reomveUserById(int uid) {
        return userMapper.delUserById(uid);
    }
}
