package com.espid.estimate.user.model;

import java.util.Date;

import lombok.Data;

@Data
public class EstimateModel {

    /* 견적서 번호 */
    private Integer estimateId;

    /* 견적서 제목 */
    private String title;

    /* 총 견적금액 */
    private int estimatePrice;

    /* 발신인 정보 */
    private Integer spidId;
    
    /* 견적 날짜*/
    private Date estimateDate;

    /* 비고 */
    private String estimateNote;

    /* 고객사 아이디 */
    private Integer customerId;
}