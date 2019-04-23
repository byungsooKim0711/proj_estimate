package com.espid.estimate.admin.tool.model;

import lombok.Data;

@Data
public class Tool {

    private Integer toolId;

    private String toolName;

    private String toolModelNumber;

    private String toolLicense;

    private Integer venderId;

    private String toolDetails;

    /* 벤더사 정보 */

    private String venderName;

    private Integer spidId;
}