package com.espid.estimate.user.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class EstimateModel {

    /* 견적서 번호 */
    private Integer estimateId;

    /* 견적서 제목 */
    private String title;

    /* 총 견적금액 (할인율 포함) */
    private int estimatePrice;

    /* 할인 전 금액 */
    private int estimateOriginPrice;

    /* 발신인 정보 */
    private Integer spidId;
    
    /* 견적 날짜*/
    private Date estimateDate;

    /* 비고 */
    private String estimateNote;

    /* 고객사 아이디 */
    private Integer customerId;

    private List<EstimateDetailModel> estimateDetailModels;
}