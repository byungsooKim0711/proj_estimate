package com.espid.estimate.admin.estimate.mapper;

import java.util.List;
import java.util.Optional;

import com.espid.estimate.admin.estimate.model.Estimate;
import com.espid.estimate.admin.estimate.model.EstimateDetail;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EstimateMapper {
    
    public List<Estimate> selectEstimate(String from, String to, @Param("search") String search) throws Exception;

    public Optional<Estimate> findEstimateById(int estimateId) throws Exception;

    public List<EstimateDetail> findEstimateDetailByEstimateId(int estimateId) throws Exception;
}