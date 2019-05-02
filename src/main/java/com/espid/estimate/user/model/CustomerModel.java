package com.espid.estimate.user.model;

import java.util.List;

import lombok.Data;

@Data
public class CustomerModel {

    private Integer customerId;

    private String customerCompany;

    private String customerName;

    private String customerTel;

    private String customerEmail;

    private List<EstimateModel> estimateModels;
}