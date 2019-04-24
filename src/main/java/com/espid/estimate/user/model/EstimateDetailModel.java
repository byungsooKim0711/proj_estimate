package com.espid.estimate.user.model;

import lombok.Data;

@Data
public class EstimateDetailModel {

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
    private String startMaintenance;

    /* 유지보수 끝 */
    private String endMaintenance;
}