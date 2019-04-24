package com.espid.estimate.admin.estimate.mapper;

import java.util.List;
import java.util.Optional;

import com.espid.estimate.admin.estimate.model.Estimate;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EstimateMapper {
    
    public List<Estimate> selectEstimate() throws Exception;

    public Optional<Estimate> findEstimateById(int estimateId) throws Exception;
}