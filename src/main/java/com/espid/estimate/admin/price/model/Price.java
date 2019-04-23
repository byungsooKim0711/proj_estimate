package com.espid.estimate.admin.price.model;

import lombok.Data;

@Data
public class Price {

    /* price */
    private Integer priceId;

    private Float eur;

    private Float exchangeRate;

    private Float krw;

    private Integer toolId;

    private int quantity;

    /* tool info */
    private String toolName;

    private String toolModelNumber;

    private String toolLicense;

    private String toolDetails;

    private Integer venderId;
}