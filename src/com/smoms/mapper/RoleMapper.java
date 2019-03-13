package com.smoms.mapper;

import com.smoms.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {
    @Select("select * from smbms_role")
    List<Role> selAllRole();

    @Select("select * from smbms_Role where id=#{userRole}")
    Role selRoleById(@Param("userRole") int userRole);
}
