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

    /* 구매 회사 */
    private String company;

    /* 구매 담당자 */
    private String incharge;

    /* 구매 회사 전화번호 */
    private String tel;

    /* 구매 회사 이메일 */
    private String email;

    /* 발신인 정보 */
    private Integer spidId;
    
    /* 견적 날짜*/
    private Date estimateDate;

    /* 비고 */
    private String estimateNote;
}