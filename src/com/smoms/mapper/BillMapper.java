package com.smoms.mapper;

import com.smoms.pojo.Bill;
import com.smoms.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BillMapper {
    @Select("select * from smbms_bill")
    List<Bill> selBills();

    @Insert("insert into smbms_bill (billCode,productName,productDesc,productUnit,productCount,totalPrice,isPayment,createdBy,creationDate,providerId) values (#{bill.billCode},#{bill.productName},#{bill.productDesc},#{bill.productUnit},#{bill.productCount},#{bill.totalPrice},#{bill.isPayment},#{bill.createdBy},#{bill.creationDate},#{bill.providerId})")
    int insBill(@Param("bill") Bill bill);

    @Select("select * from smbms_bill where id=#{billId}")
    Bill selBillById(@Param("billId") int billId);

    @Update("update smbms_bill set productName=#{bill.productName},productDesc=#{bill.productDesc},productUnit=#{bill.productUnit},productCount=#{bill.productCount},totalPrice=#{bill.totalPrice},isPayment=#{bill.isPayment},providerId=#{bill.providerId},modifyBy=#{bill.modifyBy},modifyDate=#{bill.modifyDate} where id = #{bill.id}")
    int updBillById(@Param("bill") Bill bill);

    @Delete("delete from smbms_bill where id=#{id}")
    boolean delBillById(int id);


    @Select("<script>" +
            "select * from smbms_bill " +
            "<where>" +
            "<if test='productName!=null'>" +
            "and productName LIKE CONCAT('%',#{productName},'%') " +
            "</if>" +
            "<if test='providerId!=0'>" +
            "and providerId=#{providerId} " +
            "</if>" +
            "<if test='isPayment!=0'>" +
            "and isPayment=#{isPayment} " +
            "</if>" +
            "</where>" +
            "</script>")
    List<Bill> selBillByTrem(@Param("productName") String productName, @Param("providerId") int providerId, @Param("isPayment") int isPayment);
}
