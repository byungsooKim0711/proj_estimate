package com.espid.estimate.admin.estimate.model;

import java.util.Date;

import lombok.Data;

@Data
public class Estimate {

    /* 견적서 번호 */
    private Integer estimateId;

    /* 견적서 제목 */
    private String title;

    /* 총 견적금액 */
    private int estimatePrice;

    /* 고객사 아이디 */
    private Integer customerId;

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
}