package com.espid.estimate.unittests.admin.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.espid.estimate.admin.price.controller.PriceController;
import com.espid.estimate.admin.price.model.Price;
import com.espid.estimate.admin.price.service.PriceService;
import com.espid.estimate.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

public class PriceControllerTests {

    @InjectMocks
    private PriceController priceController;

    @Mock
    private PriceService priceService;

    private static ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeClass
    public static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(priceController)
            .build();
    }

    @Test
    public void testGetPrices() throws Exception {
        
        /* arrange */
        Price price = new Price();
        price.setPriceId(1);
        price.setEur(1000f);
        price.setExchangeRate(1000f);
        price.setKrw(price.getEur() * price.getExchangeRate());
        price.setQuantity(1);

        List<Price> prices = Arrays.asList(price);

        when(priceService.selectPrices(null)).thenReturn(prices);

        String jsonString = objectMapper.writeValueAsString(prices);
        
        /* act & assert */
        mockMvc.perform (
             get("/admin/price"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(jsonString));
            
        verify(priceService, times(1)).selectPrices(null);
    }

    @Test
    public void testConditionalGetPricesWithEmptyList() throws Exception {

        /* arrange */
        List<Price> prices = new ArrayList<>();

        when(priceService.selectPrices("kimbs")).thenReturn(prices);

        String jsonString = objectMapper.writeValueAsString(prices);

        /* act & assert */
        mockMvc.perform (
             get("/admin/price")
            .param("search", "kimbs"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(jsonString));

        verify(priceService, times(1)).selectPrices("kimbs");
    }

    @Test
    public void testInsertPrice() throws Exception {

        /* arrange */
        Price price = new Price();
        price.setPriceId(1);
        price.setEur(1000f);
        price.setExchangeRate(1000f);
        price.setKrw(price.getEur() * price.getExchangeRate());
        price.setQuantity(1);

        when(priceService.insertPrice(price)).thenReturn(price);

        String jsonString = objectMapper.writeValueAsString(price);

        /* act & assert */
        mockMvc.perform (
             post("/admin/price")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString))
            .andExpect(status().isCreated())
            .andExpect(header().string("location", containsString("http://localhost/admin/price/" + price.getPriceId())))
            .andExpect(content().string(jsonString));

        verify(priceService, times(1)).insertPrice(price);
    }

    @Test
    public void testDeletePrice() throws Exception {

        /* arrange */
        Price price = new Price();
        price.setPriceId(1);
        price.setEur(1000f);
        price.setExchangeRate(1000f);
        price.setKrw(price.getEur() * price.getExchangeRate());
        price.setQuantity(1);

        when(priceService.findPriceById(price.getPriceId())).thenReturn(price);
        when(priceService.deletePrice(price.getPriceId())).thenReturn(1);

        /* act & assert */
        mockMvc.perform (
             delete("/admin/price/{priceId}", price.getPriceId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted());

        verify(priceService, times(1)).findPriceById(price.getPriceId());
        verify(priceService, times(1)).deletePrice(price.getPriceId());
    }

    @Test
    public void testDeletePriceFail404NotFound() throws Exception {

        /* arrange */
        Price price = new Price();
        price.setPriceId(Integer.MAX_VALUE);
        price.setEur(1000f);
        price.setExchangeRate(1000f);
        price.setKrw(price.getEur() * price.getExchangeRate());
        price.setQuantity(1);

        when(priceService.findPriceById(price.getPriceId())).thenThrow(new ResourceNotFoundException());

        /* act & assert */
        mockMvc.perform (
             delete("/admin/price/{priceId}", price.getPriceId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(priceService, times(1)).findPriceById(price.getPriceId());
    }

    @Test
    public void testUpdatePrice() throws Exception {

        /* arrange */
        Price price = new Price();
        price.setPriceId(1);
        price.setEur(1000f);
        price.setExchangeRate(1000f);
        price.setKrw(price.getEur() * price.getExchangeRate());
        price.setQuantity(1);

        when(priceService.findPriceById(price.getPriceId())).thenReturn(price);

        Price updated = new Price();
        updated.setPriceId(price.getPriceId());
        updated.setEur(2000f);
        updated.setExchangeRate(250f);
        updated.setKrw(price.getEur() * price.getExchangeRate());
        updated.setQuantity(4);

        when(priceService.updatePrice(price)).thenReturn(1);

        String jsonString = objectMapper.writeValueAsString(updated);

        /* act & assert */
        mockMvc.perform (
             put("/admin/price/{priceId}", price.getPriceId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(jsonString));

        verify(priceService, times(1)).findPriceById(price.getPriceId());
        verify(priceService, times(1)).updatePrice(updated);
    }

    @Test
    public void testUpdatePriceFail404NotFound() throws Exception {

        /* arrange */
        Price price = new Price();
        price.setPriceId(Integer.MAX_VALUE);
        price.setEur(1000f);
        price.setExchangeRate(1000f);
        price.setKrw(price.getEur() * price.getExchangeRate());
        price.setQuantity(1);

        when(priceService.findPriceById(price.getPriceId())).thenThrow(new ResourceNotFoundException());

        String jsonString = objectMapper.writeValueAsString(price);

        /* act & assert */
        mockMvc.perform (
             put("/admin/price/{priceId}", price.getPriceId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString))
            .andExpect(status().isNotFound());

        verify(priceService, times(1)).findPriceById(price.getPriceId());
    }
}