package com.espid.estimate.user.service;

import java.util.List;

import com.espid.estimate.user.mapper.UserEstimateMapper;
import com.espid.estimate.user.model.EstimateDetailModel;
import com.espid.estimate.user.model.EstimateModel;
import com.espid.estimate.user.model.EstimateWholeModel;
import com.espid.estimate.user.model.SenderModel;
import com.espid.estimate.user.model.ToolModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEstimateService {

    @Autowired
    private UserEstimateMapper userEstimateMapper;

    public SenderModel selectSenderByName(String senderName) {
        SenderModel sender = userEstimateMapper.selectSenderByName(senderName);
        sender.setVenders(userEstimateMapper.selectVenderBySenderId(sender.getSpidId()));

        return sender;
    }

    /* OLD */
    /* public List<ToolModel> getToolListByVenderId(Integer venderId) {
        List<ToolModel> toolList = userEstimateMapper.getToolListByVenderId(venderId);
        
        toolList.stream().forEach(t -> {
            List<PriceModel> priceList = userEstimateMapper.getPriceListByToolId(t.getToolId());
            t.setPriceList(priceList);
        });
        return toolList;
    } */

    /* NEW - 2019-04-22 */
    public List<ToolModel> getToolListByVenderId(Integer venderId) {
        List<ToolModel> toolList = userEstimateMapper.getToolListByVenderId(venderId);
        return toolList;
    }

    public boolean saveWholeEstimate(EstimateWholeModel estimateWholeModel) {
        EstimateModel estimateModel = estimateWholeModel.getEstimateModel();

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

    public int saveEstimate(EstimateModel estimateModel) {
        return userEstimateMapper.saveEstimate(estimateModel);
    }

    public int saveEstimateDetail(List<EstimateDetailModel> estimateDetailModels) {
        return userEstimateMapper.saveEstimateDetail(estimateDetailModels);
    }
}