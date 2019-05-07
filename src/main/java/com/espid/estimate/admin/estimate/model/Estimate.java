package com.espid.estimate.admin.estimate.model;

import java.util.Date;

import lombok.Data;

@Data
public class Estimate {

    /* 견적서 번호 */
    private Integer estimateId;

    /* 견적서 제목 */
    private String title;

    /* 총 견적금액 (할인 포함)*/
    private int estimatePrice;

    /* 할인 전 금액 */
    private int estimateOriginPrice;

    /* 견적 날짜*/
    private Date estimateDate;

    /* 비고 */
    private String estimateNote;

    /* 발신인 정보 */
    private Integer spidId;

    private String spidDept;

    private String spidName;
    
    private String spidTel1;

    private String spidTel2;

    private String spidEmail;

    private String job;

    /* 고객사 아이디 */
    private Integer customerId;

    private String customerCompany;

    private String customerName;

    private String customerTel;

    private String customerEmail;
}