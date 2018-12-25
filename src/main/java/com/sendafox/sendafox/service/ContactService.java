package com.sayhellostream.sayhellostream.service;


import com.sayhellostream.sayhellostream.domain.Contact;
import com.sayhellostream.sayhellostream.domain.Payment;

public interface ContactService extends BaseService {

    int activeContactCount();

    String totalMonthlyRevenue();

    Contact get(String id);
    void delete(String id);
    void save(Contact student);
    Iterable<Contact> findAll();

    Payment addPayment(String studentId);
    Payment getPayment(String id);
    void deletePayment(String id);
    void savePayment(Payment payment);
}