package com.smoms.mapper;

import com.smoms.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    /**
     * 查询所有用户
     *
     * @return
     */
    @Select("select * from smbms_user")
    List<User> selAllUser();

    /**
     * 用户登陆
     *
     * @param userCode
     * @param userPassword
     * @return
     */
    @Select("select * from smbms_user where userCode=#{userCode} and userPassword=#{userPassword}")
    User selUserByCP(@Param("userCode") String userCode, @Param("userPassword") String userPassword);

    @Update("update smbms_user set userPassword=#{userPassword} where id=#{id}")
    int updUpwdById(@Param("userPassword") String userPassword, @Param("id") int id);

    @Select("<script>" +
            "select * from smbms_user " +
            "<where>" +
            "<if test='userName!=null'>" +
            "and userName LIKE CONCAT('%',#{userName},'%') " +
            "</if>" +
            "<if test='roleId!=0'>" +
            "and userRole=#{roleId} " +
            "</if>" +
            "</where>" +
            "</script>")
    List<User> selUserByTerm(@Param("userName") String userName, @Param("roleId") int userRole);

    @Select("select userCode from smbms_user where userCode=#{userCode}")
    User selUserByUserCode(@Param("userCode") String userCode);

    @Insert("insert into smbms_user (userCode,userName,userPassword,gender,birthday,phone,address,userRole,createdBy,creationDate) values" +
            " (#{user.userCode},#{user.userName},#{user.userPassword},#{user.gender},#{user.birthday},#{user.phone},#{user.address},#{user.userRole},#{user.createdBy},#{user.creationDate})")
    int insUser(@Param("user") User user);

    @Select("select * from smbms_user where id=#{id}")
    User selUserById(@Param("id") int id);

    @Update("update smbms_user set userName=#{user.userName},gender=#{user.gender},birthday=#{user.birthday},phone=#{user.phone},address=#{user.address},userRole=#{user.userRole},modifyBy=#{user.modifyBy},modifyDate=#{user.modifyDate} where id=#{uid}")
    int updUserById(@Param("user") User user, @Param("uid") int uid);

    @Delete("delete from smbms_user where id=#{uid}")
    boolean delUserById(@Param("uid") int uid);
}
