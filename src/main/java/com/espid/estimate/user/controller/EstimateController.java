package com.espid.estimate.user.controller;

import java.util.List;

import com.espid.estimate.user.model.EstimateWholeModel;
import com.espid.estimate.user.model.SenderModel;
import com.espid.estimate.user.model.ToolModel;
import com.espid.estimate.user.service.UserEstimateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EstimateController {

    @Autowired
    private UserEstimateService userEstimateService;

    @GetMapping("/getSender/{senderName}")
    public ResponseEntity<SenderModel> getSender(@PathVariable String senderName) {
        SenderModel sender = userEstimateService.selectSenderByName(senderName);

        if (sender == null) {
            return new ResponseEntity<SenderModel>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<SenderModel>(sender, HttpStatus.OK);
    }

    @GetMapping("/getToolList/{venderId}")
    public ResponseEntity<List<ToolModel>> getToolListByVenderId(@PathVariable Integer venderId) {
        return new ResponseEntity<>(userEstimateService.getToolListByVenderId(venderId), HttpStatus.OK);
    }
    
    @PostMapping("/estimate")
    public ResponseEntity<EstimateWholeModel> saveEstimate(@RequestBody EstimateWholeModel estimateWholeModel) {
        
        if (!userEstimateService.saveWholeEstimate(estimateWholeModel)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(estimateWholeModel, HttpStatus.OK);
    }
}