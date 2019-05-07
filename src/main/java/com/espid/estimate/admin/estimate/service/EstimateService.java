package com.espid.estimate.admin.estimate.service;

import java.util.List;

import com.espid.estimate.admin.estimate.mapper.EstimateMapper;
import com.espid.estimate.admin.estimate.model.Estimate;
import com.espid.estimate.admin.estimate.model.EstimateDetail;
import com.espid.estimate.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstimateService {
  
    @Autowired
    private EstimateMapper estimateMapper;
    
    public List<Estimate> selectEstimate(String from, String to, String search) throws Exception {
        return estimateMapper.selectEstimate(from, to, search);
    }

    public Estimate findEstimateById(int estimateId) throws Exception {
        return estimateMapper.findEstimateById(estimateId).orElseThrow(() -> new ResourceNotFoundException("Estimate resource not found with ID: " + estimateId));
    }

    public List<EstimateDetail> findEstimateDetailByEstimateId(int estimateId) throws Exception {
        return estimateMapper.findEstimateDetailByEstimateId(estimateId);
    }
}