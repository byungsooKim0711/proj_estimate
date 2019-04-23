package com.espid.estimate.admin.price.service;

import java.util.List;

import com.espid.estimate.admin.price.mapper.PriceMapper;
import com.espid.estimate.admin.price.model.Price;
import com.espid.estimate.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    @Autowired
    private PriceMapper priceMapper;

    public List<Price> selectPrices(String search) {
        return priceMapper.selectPrices(search);
    }

    /**
     * 
     * @param priceId
     * @return Price
     * @throws ResourceNotFoundException
      */
    public Price findPriceById(int priceId) {
        return priceMapper.findPriceById(priceId).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    public Price insertPrice(Price price) {
        priceMapper.insertPrice(price);
        return price;
    }

    public int deletePrice(int priceId) {
        return priceMapper.deletePrice(priceId);
    }

    public int updatePrice(Price price) {
        return priceMapper.updatePrice(price);
    } 
}