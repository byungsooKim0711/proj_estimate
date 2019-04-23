package com.espid.estimate.admin.vender.model;

import lombok.Data;

@Data
public class Vender {

    private Integer venderId;

    private String venderName;

    private Integer spidId;

    /* sender 정보 */
    private String spidDept;

    private String spidName;
    
    private String spidTel1;

    private String spidTel2;

    private String spidEmail;

    private String job;
}