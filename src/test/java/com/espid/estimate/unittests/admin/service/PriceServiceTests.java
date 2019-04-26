package com.espid.estimate.unittests.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.espid.estimate.admin.price.mapper.PriceMapper;
import com.espid.estimate.admin.price.model.Price;
import com.espid.estimate.admin.price.service.PriceService;
import com.espid.estimate.exception.ResourceNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PriceServiceTests {
    
    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private PriceService priceService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSelectPrices() throws Exception {

        /* arrange */
        List<Price> prices = new ArrayList<>();
        Price price = new Price();
        price.setPriceId(1);
        price.setEur(7000f);
        price.setExchangeRate(1200f);
        price.setKrw(price.getEur() * price.getExchangeRate());
        price.setQuantity(1);

        prices.add(price);

        when(priceMapper.selectPrices(null)).thenReturn(prices);

        /* act */
        List<Price> response = priceService.selectPrices(null);

        /* assert */
        assertThat(response).hasSize(1).allSatisfy(p -> assertThat(p).isEqualTo(price));
    }

    @Test
    public void testConditionalSelectPrices() throws Exception {
        
        /* arrange*/
        List<Price> prices = new ArrayList<>();
        Price price1 = new Price();
        price1.setPriceId(1);
        price1.setEur(3000f);
        price1.setExchangeRate(1200f);
        price1.setKrw(price1.getEur() * price1.getExchangeRate());
        price1.setQuantity(1);
        price1.setToolName("kimbs");
        price1.setToolId(1);


        Price price2 = new Price();
        price2.setPriceId(2);
        price2.setEur(7000f);
        price2.setExchangeRate(1200f);
        price2.setKrw(price2.getEur() * price2.getExchangeRate());
        price2.setQuantity(1);
        price1.setToolName("kkkk");
        price1.setToolId(1);

        prices.add(price1);
        prices.add(price2);

        List<Price> conditionalPrices = new ArrayList<>();
        conditionalPrices.add(price1);

        when(priceMapper.selectPrices(null)).thenReturn(prices);
        when(priceMapper.selectPrices("bs")).thenReturn(conditionalPrices);

        /* act */
        List<Price> response1 = priceService.selectPrices(null);
        List<Price> response2 = priceService.selectPrices("bs");

        /* assert */
        assertThat(response1).hasSize(2).contains(price1, price2);
        assertThat(response2).hasSize(1).contains(price1);
        
    }

    @Test
    public void testFindPriceById() throws Exception {

        /* arrange */
        Price price = new Price();
        price.setPriceId(1);
        price.setEur(7000f);
        price.setExchangeRate(1200f);
        price.setKrw(price.getEur() * price.getExchangeRate());
        price.setQuantity(1);

        when(priceMapper.findPriceById(1)).thenReturn(Optional.of(price));
        
        /* act */
        Price response = priceService.findPriceById(price.getPriceId());

        /* assert */
        assertEquals(price, response);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testFindPriceByIdWithThrowException() throws Exception {

        /* arrange */
        Price price = new Price();
        price.setPriceId(1);
        price.setEur(7000f);
        price.setExchangeRate(1200f);
        price.setKrw(price.getEur() * price.getExchangeRate());
        price.setQuantity(1);

        when(priceMapper.findPriceById(123456)).thenReturn(Optional.of(price));
        
        /* act */
        Price response = priceService.findPriceById(price.getPriceId());

        /* assert */
        assertEquals(price, response);
    }

    @Test
    public void testInsertPrice() throws Exception {

        /* arrange */
        Price price = new Price();
        price.setPriceId(1);
        price.setEur(7000f);
        price.setExchangeRate(1200f);
        price.setKrw(price.getEur() * price.getExchangeRate());
        price.setQuantity(1);

        when(priceMapper.insertPrice(price)).thenReturn(1);

        /* act */
        Price response = priceService.insertPrice(price);

        /* assert */
        assertEquals(price, response);
    }

    @Test
    public void testDeletePrice() throws Exception {

        /* arrange */
        when(priceMapper.deletePrice(1234)).thenReturn(1);

        /* act */
        int response = priceService.deletePrice(1234);

        /* assert */         
        assertEquals(1, response);
    }

    @Test
    public void testUpdatePrice() throws Exception {

        /* arrange */
        Price price = new Price();
        price.setPriceId(1);
        price.setEur(7000f);
        price.setExchangeRate(1200f);
        price.setKrw(price.getEur() * price.getExchangeRate());
        price.setQuantity(1);

        when(priceMapper.insertPrice(price)).thenReturn(1);
        
        price.setQuantity(2);

        when(priceMapper.updatePrice(price)).thenReturn(1);

        /* act */
        int response = priceService.updatePrice(price);

        /* assert */
        assertEquals(1, response);
    }
}