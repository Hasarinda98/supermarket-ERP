package com.bci.productcrud.service;

import com.bci.productcrud.model.SalesReceipt;

import java.util.List;

public interface SalesReceiptService {

    List<SalesReceipt> getAllSalesReceipts();

    SalesReceipt getSalesReceiptById(Long id);

    SalesReceipt saveSalesReceipt(SalesReceipt salesReceipt);

    void deleteSalesReceipt(Long id);
}