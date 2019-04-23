package com.espid.estimate.user.mapper;

import java.util.List;

import com.espid.estimate.user.model.PriceModel;
import com.espid.estimate.user.model.SenderModel;
import com.espid.estimate.user.model.ToolModel;
import com.espid.estimate.user.model.VenderModel;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserSenderMapper {
    public SenderModel selectSenderByName(String senderName);

    public List<VenderModel> selectVenderBySenderId(int senderId);

    public List<ToolModel> getToolListByVenderId(Integer venderId);

    public List<PriceModel> getPriceListByToolId(Integer toolId);
}