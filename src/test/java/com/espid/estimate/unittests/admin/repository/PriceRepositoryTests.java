package com.espid.estimate.unittests.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import com.espid.estimate.admin.price.mapper.PriceMapper;
import com.espid.estimate.admin.price.model.Price;
import com.espid.estimate.admin.tool.mapper.ToolMapper;
import com.espid.estimate.admin.tool.model.Tool;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MybatisTest
public class PriceRepositoryTests {
    
    @Autowired
    private PriceMapper priceMapper;

    @Autowired
    private ToolMapper toolMapper;

    private Tool tool;

    @Before
    public void setup() throws Exception {
        tool = new Tool();
        tool.setToolDetails("Tool Details");
        tool.setToolId(1234567890);
        tool.setToolLicense("Tool License");
        tool.setToolModelNumber("Tool Model Number");
        tool.setToolName("Tool Name");
        /* tool.setVenderId(1); */ /* 외래키 때문에 설정 No No!! */

        toolMapper.insertTool(tool);
    }

    @Test
    public void shouldFindNoPricesIfrepositoryIsEmpty() throws Exception {
        /* arrange */
        String search = null;

        /* act */
        List<Price> prices = priceMapper.selectPrices(search);

        /* assert */
        assertThat(prices).isEmpty();
    }

    @Test
    public void shouldStoreAPrices() throws Exception {
        /* arrange */
        Price price = new Price();
        price.setPriceId(1111);
        price.setQuantity(1);
        price.setExchangeRate(1000f);
        price.setEur(1234.5f);
        price.setKrw(1234500f);
        price.setToolId(1234567890);

        /* act */
        int actual = priceMapper.insertPrice(price);

        /* assert */
        assertEquals(1, actual);
    }

    @Test
    public void conditionalSearchWithStringKimbs() throws Exception {
        /* arrange */
        Price price = new Price();
        price.setPriceId(1111);
        price.setQuantity(1);
        price.setExchangeRate(1000f);
        price.setEur(1234.5f);
        price.setKrw(1234500f);
        price.setToolId(1234567890);

        price.setToolDetails(tool.getToolDetails());
        price.setToolId(tool.getToolId());
        price.setToolLicense(tool.getToolLicense());
        price.setToolModelNumber(tool.getToolModelNumber());
        price.setToolName(tool.getToolName());

        priceMapper.insertPrice(price);

        /* act */
        List<Price> prices = priceMapper.selectPrices("Name");
        
        /* assert */
        assertThat(prices).hasSize(1).allSatisfy(p -> assertThat(p).isEqualTo(price));
    }

    @Test
    public void shouldFindPriceById() throws Exception {
        /* arrange */
        Price price1 = new Price();
        price1.setPriceId(1111);
        price1.setQuantity(1);
        price1.setExchangeRate(1234f);
        price1.setEur(1000f);
        price1.setKrw(1234000f);
        price1.setToolId(1234567890);

        Price price2 = new Price();
        price2.setPriceId(2222);
        price2.setQuantity(1);
        price2.setExchangeRate(4321f);
        price2.setEur(1000f);
        price2.setKrw(4321000f);
        price2.setToolId(1234567890);

        priceMapper.insertPrice(price1);
        priceMapper.insertPrice(price2);

        /* act */
        Price found = priceMapper.findPriceById(2222).get();

        /* assert */
        assertEquals(price2, found);
    }

    @Test
    public void shouldFindAllPrices() throws Exception {
        /* arrange */
        Price price1 = new Price();
        price1.setPriceId(1111);
        price1.setQuantity(1);
        price1.setExchangeRate(1234f);
        price1.setEur(1000f);
        price1.setKrw(1234000f);
        price1.setToolId(1234567890);

        price1.setToolDetails(tool.getToolDetails());
        price1.setToolId(tool.getToolId());
        price1.setToolLicense(tool.getToolLicense());
        price1.setToolModelNumber(tool.getToolModelNumber());
        price1.setToolName(tool.getToolName());

        Price price2 = new Price();
        price2.setPriceId(2222);
        price2.setQuantity(1);
        price2.setExchangeRate(300f);
        price2.setEur(1000f);
        price2.setKrw(300000f);
        price2.setToolId(1234567890);

        price2.setToolDetails(tool.getToolDetails());
        price2.setToolId(tool.getToolId());
        price2.setToolLicense(tool.getToolLicense());
        price2.setToolModelNumber(tool.getToolModelNumber());
        price2.setToolName(tool.getToolName());

        priceMapper.insertPrice(price1);
        priceMapper.insertPrice(price2);

        /* act */
        List<Price> prices = priceMapper.selectPrices(null);

        /* assert */
        assertThat(prices).hasSize(2).contains(price1, price2);
    }

    @Test
    public void shouldChangedPriceInfomation() throws Exception {
        /* arrange */
        Price price1 = new Price();
        price1.setPriceId(1111);
        price1.setQuantity(1);
        price1.setExchangeRate(1234f);
        price1.setEur(1000f);
        price1.setKrw(1234000f);
        price1.setToolId(1234567890);

        priceMapper.insertPrice(price1);

        /* act */
        int actual = priceMapper.updatePrice(price1);

        /* assert */
        assertEquals(1, actual);

        Price found = priceMapper.findPriceById(1111).get();
        assertEquals(price1, found);
    }

    @Test
    public void whenThePriceIsDeleted() throws Exception {
        /* arrange */
        Price price1 = new Price();
        price1.setPriceId(36789);
        price1.setQuantity(1);
        price1.setExchangeRate(1234f);
        price1.setEur(1000f);
        price1.setKrw(1234000f);
        price1.setToolId(1234567890);

        priceMapper.insertPrice(price1);

        /* act */
        int actual = priceMapper.deletePrice(36789);

        /* assert */
        assertEquals(1, actual);

        List<Price> prices = priceMapper.selectPrices(null);
        assertThat(prices).hasSize(0);
    }
}