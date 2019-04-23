package com.espid.estimate.user.service;

import java.util.List;

import com.espid.estimate.user.mapper.UserSenderMapper;
import com.espid.estimate.user.model.SenderModel;
import com.espid.estimate.user.model.ToolModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSenderService {

    @Autowired
    private UserSenderMapper userSenderMapper;

    public SenderModel selectSenderByName(String senderName) {
        SenderModel sender = userSenderMapper.selectSenderByName(senderName);
        sender.setVenders(userSenderMapper.selectVenderBySenderId(sender.getSpidId()));

        return sender;
    }

    /* OLD */
    /* public List<ToolModel> getToolListByVenderId(Integer venderId) {
        List<ToolModel> toolList = userSenderMapper.getToolListByVenderId(venderId);
        
        toolList.stream().forEach(t -> {
            List<PriceModel> priceList = userSenderMapper.getPriceListByToolId(t.getToolId());
            t.setPriceList(priceList);
        });
        return toolList;
    } */

    /* NEW - 2019-04-22 */
    public List<ToolModel> getToolListByVenderId(Integer venderId) {
        List<ToolModel> toolList = userSenderMapper.getToolListByVenderId(venderId);
        return toolList;
    }
}