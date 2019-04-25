package com.espid.estimate.admin.estimate.controller;

import java.util.List;

import com.espid.estimate.admin.estimate.model.Estimate;
import com.espid.estimate.admin.estimate.model.EstimateDetail;
import com.espid.estimate.admin.estimate.service.EstimateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class EstimateController {

    @Autowired
    private EstimateService estimateService;

    @GetMapping("/estimate")
    public List<Estimate> getEstimates (
          @RequestParam(name = "from") String from
        , @RequestParam(name = "to") String to
        , @RequestParam(name = "search", required = false) String search) throws Exception {

        return estimateService.selectEstimate(from, to, search);
    }

    @GetMapping("/estimate/{estimateId}/estimateDetail")
    public List<EstimateDetail> findEstimateDetailByEstimateId(@PathVariable(name = "estimateId") int estimateId) throws Exception {
        return estimateService.findEstimateDetailByEstimateId(estimateId);
    }
}