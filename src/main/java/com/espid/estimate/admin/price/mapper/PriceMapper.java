package com.espid.estimate.admin.price.mapper;

import java.util.List;
import java.util.Optional;

import com.espid.estimate.admin.price.model.Price;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PriceMapper {

    public List<Price> selectPrices(@Param("search") String search) throws Exception;

    public Optional<Price> findPriceById(int priceId) throws Exception;

    public int insertPrice(Price price) throws Exception;

    public int deletePrice(int price) throws Exception;

    public int updatePrice(Price price) throws Exception;
}