package com.smoms.service;

import com.smoms.pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> allRoles();

    Role findRoleById(int userRole);
}
