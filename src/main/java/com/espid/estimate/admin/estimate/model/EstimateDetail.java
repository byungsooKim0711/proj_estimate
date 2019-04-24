package com.espid.estimate.admin.estimate.model;

import java.util.Date;

import lombok.Data;

@Data
public class EstimateDetail {

    /* 견적서 상세 아이디 */
    private Integer estimateDetailId;

    /* 상세내역 */
    private String estimateDetails;

    /* 수량 */
    private int quantity;

    /* 단위 */
    private String unit; /* copy ? */

    /* 단가 */
    private int unitPrice;

    /* 제안가 */
    private int supplyPrice;

    /* 견적서 번호 */
    private Integer estimateId;

    /* 할인율 */
    private int discountRate;

    /* 유지보수 시작 */
    private Date startMaintenance;

    /* 유지보수 끝 */
    private Date endMaintenance;
}