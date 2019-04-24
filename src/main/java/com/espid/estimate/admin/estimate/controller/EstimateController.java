package com.espid.estimate.admin.estimate.controller;

import java.util.List;

import com.espid.estimate.admin.estimate.model.Estimate;
import com.espid.estimate.admin.estimate.service.EstimateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class EstimateController {

    @Autowired
    private EstimateService estimateService;

    @GetMapping("/estimate")
    public List<Estimate> getEstimates() throws Exception {
        return estimateService.selectEstimate();
    }
}