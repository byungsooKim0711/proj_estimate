package com.espid.estimate.admin.price.mapper;

import java.util.List;
import java.util.Optional;

import com.espid.estimate.admin.price.model.Price;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PriceMapper {

    public List<Price> selectPrices(@Param("search") String search);

    public Optional<Price> findPriceById(int priceId);

    public int insertPrice(Price price);

    public int deletePrice(int price);

    public int updatePrice(Price price);
}