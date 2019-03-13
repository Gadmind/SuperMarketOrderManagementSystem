package com.smoms.service.impl;

import com.smoms.mapper.RoleMapper;
import com.smoms.pojo.Role;
import com.smoms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> allRoles() {
        return roleMapper.selAllRole();
    }

    @Override
    public Role findRoleById(int userRole) {
        return roleMapper.selRoleById(userRole);
    }
}
