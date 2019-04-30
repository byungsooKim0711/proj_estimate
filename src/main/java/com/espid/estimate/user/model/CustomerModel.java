package com.espid.estimate.user.model;

import lombok.Data;

@Data
public class CustomerModel {

    private Integer customerId;

    private String customerCompany;

    private String customerName;

    private String customerTel;

    private String customerEmail;
}