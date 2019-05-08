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

    /* 도구이름 */
    private String toolName;

    /* 라이센스 */
    private String toolLicense;

    /* 업그레이드 여부 */
    private String upgrade;

    /* 업그레이드 전 도구 이름 */
    private String beforeToolName;

    /* 업그레이드 전 도구 라이센스 */
    private String beforeToolLicense;

    /* 업그레이드된 개수 */
    private int upgradeQuantity;
}