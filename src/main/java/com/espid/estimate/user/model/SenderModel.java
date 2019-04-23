package com.espid.estimate.user.model;

import java.util.List;

import lombok.Data;

@Data
public class SenderModel {

    private int spidId;

    private String spidDept;

    private String spidName;

    private String spidTel1;

    private String spidTel2;

    private String spidEmail;

    private String job;

    private List<VenderModel> venders;
}