package com.smoms.service.impl;

import com.smoms.mapper.BillMapper;
import com.smoms.pojo.Bill;
import com.smoms.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillMapper billMapper;

    @Override
    public List<Bill> showAllBills() {
        return billMapper.selBills();
    }

    @Override
    public int addBill(Bill bill) {
        return billMapper.insBill(bill);
    }

    @Override
    public Bill findBillById(int billId) {
        return billMapper.selBillById(billId);
    }

    @Override
    public int modifyBillById(Bill bill) {
        return billMapper.updBillById(bill);
    }

    @Override
    public boolean reomveBillById(int id) {
        return billMapper.delBillById(id);
    }

    @Override
    public List<Bill> findBillByTerm(String productName,int providerId,int isPayment) {
        return billMapper.selBillByTrem(productName,providerId,isPayment);
    }

}
