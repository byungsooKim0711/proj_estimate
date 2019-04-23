package com.espid.estimate.user.model;

import java.util.List;

import lombok.Data;

@Data
public class ToolModel {

    private int toolId;

    private String toolName;

    private String toolModelNumber;

    private String toolLicense;

    private int venderId;

    private String toolDetails;

    // 도구 가격
    private List<PriceModel> priceList;
}