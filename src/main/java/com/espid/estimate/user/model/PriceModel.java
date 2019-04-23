package com.espid.estimate.user.model;

import lombok.Data;

@Data
public class PriceModel {

    private int priceId;

    private float krw;

    private float exchangeRate;

    private int toolId;

    private float eur;

    private int quantity;
}