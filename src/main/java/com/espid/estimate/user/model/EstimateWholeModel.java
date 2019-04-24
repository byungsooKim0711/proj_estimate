package com.espid.estimate.user.model;

import java.util.List;

import lombok.Data;

@Data
public class EstimateWholeModel {
    
    private EstimateModel estimateModel;

    private List<EstimateDetailModel> estimateDetailModels;
}