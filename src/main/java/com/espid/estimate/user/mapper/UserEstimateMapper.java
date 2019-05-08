package com.espid.estimate.user.mapper;

import java.util.List;

import com.espid.estimate.user.model.CustomerModel;
import com.espid.estimate.user.model.EstimateDetailModel;
import com.espid.estimate.user.model.EstimateModel;
import com.espid.estimate.user.model.PriceModel;
import com.espid.estimate.user.model.SenderModel;
import com.espid.estimate.user.model.ToolModel;
import com.espid.estimate.user.model.ToolNameAndLicense;
import com.espid.estimate.user.model.VenderModel;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserEstimateMapper {
    public SenderModel selectSenderByName(String senderName) throws Exception;

    public List<VenderModel> selectVenderBySenderId(int senderId) throws Exception;

    public List<ToolModel> getToolListByVenderId(Integer venderId) throws Exception;

    public List<PriceModel> getPriceListByToolId(Integer toolId) throws Exception;

    public int saveEstimate(EstimateModel estimateModel) throws Exception;

    public int saveEstimateDetail(List<EstimateDetailModel> estimateDetailModels) throws Exception;

    public List<CustomerModel> selectCustomers(@Param("search") String search) throws Exception;

    public int insertcustomer(CustomerModel customer) throws Exception;

    public List<EstimateModel> selectEstimateByCustomerId(int customerId);
}