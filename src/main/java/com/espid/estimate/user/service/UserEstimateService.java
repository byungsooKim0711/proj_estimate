package com.espid.estimate.user.service;

import java.util.List;

import com.espid.estimate.annotation.transactional.WriteTransactional;
import com.espid.estimate.user.mapper.UserEstimateMapper;
import com.espid.estimate.user.model.CustomerModel;
import com.espid.estimate.user.model.EstimateDetailModel;
import com.espid.estimate.user.model.EstimateModel;
import com.espid.estimate.user.model.EstimateWholeModel;
import com.espid.estimate.user.model.SenderModel;
import com.espid.estimate.user.model.ToolNameAndLicense;
import com.espid.estimate.user.model.ToolModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEstimateService {

    @Autowired
    private UserEstimateMapper userEstimateMapper;

    public SenderModel selectSenderByName(String senderName) throws Exception {
        SenderModel sender = userEstimateMapper.selectSenderByName(senderName);
        sender.setVenders(userEstimateMapper.selectVenderBySenderId(sender.getSpidId()));

        return sender;
    }

    public List<ToolModel> getToolListByVenderId(Integer venderId) throws Exception {
        List<ToolModel> toolList = userEstimateMapper.getToolListByVenderId(venderId);
        return toolList;
    }

    @WriteTransactional
    public boolean saveWholeEstimate(EstimateWholeModel estimateWholeModel) throws Exception {

        /* 고객사 정보가 있는가? */
        CustomerModel customerModel = estimateWholeModel.getCustomerModel();
        
        /* 고객사 정보가 없으면, 새로운 고객사면 등록 */
        if (customerModel.getCustomerId() == null) {
            insertCustomer(customerModel);
        }

        EstimateModel estimateModel = estimateWholeModel.getEstimateModel();
        
        if (estimateModel.getCustomerId() == null) {
            estimateModel.setCustomerId(customerModel.getCustomerId());
        }

        /* 견적서 저장 */
        if (this.saveEstimate(estimateModel) == 1) {
            List<EstimateDetailModel> estimateDetailModels = estimateWholeModel.getEstimateDetailModels();
            estimateDetailModels.forEach(edm -> {
                edm.setEstimateId(estimateModel.getEstimateId());
            });

            /* 견적 상세 내용 저장 */
            if (this.saveEstimateDetail(estimateDetailModels) >= 1) {
                return true;
            }
        }
        return false;
    }

    public int saveEstimate(EstimateModel estimateModel) throws Exception {
        return userEstimateMapper.saveEstimate(estimateModel);
    }

    public int saveEstimateDetail(List<EstimateDetailModel> estimateDetailModels) throws Exception {
        return userEstimateMapper.saveEstimateDetail(estimateDetailModels);
    }

    public List<CustomerModel> selectCustomers(String search, List<ToolNameAndLicense> test) throws Exception {
        List<CustomerModel> customerModels = userEstimateMapper.selectCustomers(search);

        customerModels.forEach(c -> {
            c.setEstimateModels(userEstimateMapper.selectEstimateByCustomerId(c.getCustomerId(), test));
        });

        return customerModels;
    }

    public CustomerModel insertCustomer(CustomerModel customer) throws Exception {
        userEstimateMapper.insertcustomer(customer);
        return customer;
    }
}