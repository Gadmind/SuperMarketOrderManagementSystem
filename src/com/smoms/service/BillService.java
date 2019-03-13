package com.smoms.service;

import com.smoms.pojo.Bill;

import java.util.List;

public interface BillService {
    List<Bill> showAllBills();

    int addBill(Bill bill);

    Bill findBillById(int billId);

    int modifyBillById(Bill bill);

    boolean reomveBillById(int id);

    List<Bill> findBillByTerm(String productName,int providerId,int isPayment);
}
