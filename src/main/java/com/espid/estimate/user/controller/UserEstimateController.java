package com.espid.estimate.user.controller;

import java.util.List;

import com.espid.estimate.user.model.CustomerModel;
import com.espid.estimate.user.model.EstimateWholeModel;
import com.espid.estimate.user.model.SenderModel;
import com.espid.estimate.user.model.ToolModel;
import com.espid.estimate.user.service.UserEstimateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class UserEstimateController {

    @Autowired
    private UserEstimateService userEstimateService;

    @GetMapping("/getSender/{senderName}")
    public ResponseEntity<SenderModel> getSender(@PathVariable String senderName) throws Exception {
        SenderModel sender = userEstimateService.selectSenderByName(senderName);

        if (sender == null) {
            return new ResponseEntity<SenderModel>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<SenderModel>(sender, HttpStatus.OK);
    }

    @GetMapping("/getToolList/{venderId}")
    public ResponseEntity<List<ToolModel>> getToolListByVenderId(@PathVariable Integer venderId) throws Exception {
        return new ResponseEntity<>(userEstimateService.getToolListByVenderId(venderId), HttpStatus.OK);
    }
    
    @PostMapping("/estimate")
    public ResponseEntity<EstimateWholeModel> saveEstimate(@RequestBody EstimateWholeModel estimateWholeModel, final UriComponentsBuilder uriBuilder) throws Exception {

        if (!userEstimateService.saveWholeEstimate(estimateWholeModel)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/api/estimate/{estimateId}").buildAndExpand(estimateWholeModel.getEstimateModel().getEstimateId()).toUri());

        return new ResponseEntity<>(estimateWholeModel, headers, HttpStatus.CREATED);
    }

    @GetMapping("/customer")
    public List<CustomerModel> getCustomers(@RequestParam(name = "search") String search) throws Exception {
        return userEstimateService.selectCustomers(search);
    }
}