package com.espid.estimate.unittests.admin.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.espid.estimate.admin.vender.controller.VenderController;
import com.espid.estimate.admin.vender.model.Vender;
import com.espid.estimate.admin.vender.service.VenderService;
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

public class VenderControllerTests {

    @InjectMocks
    private VenderController venderController;

    private MockMvc mockMvc;

    private static ObjectMapper objectMapper;

    @Mock
    private VenderService venderService;

    @BeforeClass
    public static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(venderController)
            .build();
    }

    @Test
    public void testGetVenders() throws Exception {

        /* arrange */
        Vender vender1 = new Vender();
        vender1.setVenderId(1);
        vender1.setVenderName("vender name1");

        Vender vender2 = new Vender();
        vender2.setVenderId(2);
        vender2.setVenderName("vender name2");

        List<Vender> venders = Arrays.asList(vender1, vender2);

        when(venderService.selectVenders(null)).thenReturn(venders);

        String jsonString = objectMapper.writeValueAsString(venders);

        /* act & assert*/
        mockMvc.perform (
             get("/admin/vender"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(jsonString));

        verify(venderService, times(1)).selectVenders(null);
    }

    @Test
    public void testConditionalGetVenders() throws Exception {

        /* arrange */
        Vender vender1 = new Vender();
        vender1.setVenderId(1);
        vender1.setVenderName("kimbs");

        Vender vender2 = new Vender();
        vender2.setVenderId(2);
        vender2.setVenderName("vender name2");

        List<Vender> venders = Arrays.asList(vender1);

        when(venderService.selectVenders("kimbs")).thenReturn(venders);

        String jsonString = objectMapper.writeValueAsString(venders);

        /* act & assert*/
        mockMvc.perform (
             get("/admin/vender")
            .param("search", "kimbs"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(jsonString));

        verify(venderService, times(1)).selectVenders("kimbs");
    }

    @Test
    public void testInsertVender() throws Exception {

        /* arrange */
        Vender vender = new Vender();
        vender.setVenderId(1);
        vender.setVenderName("vender name1");

        when(venderService.insertVender(vender)).thenReturn(vender);

        String jsonString = objectMapper.writeValueAsString(vender);

        /* act & assert */
        mockMvc.perform (
             post("/admin/vender")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString))
            .andExpect(status().isCreated())
            .andExpect(header().string("location", containsString("http://localhost/admin/vender/" + vender.getVenderId())))
            .andExpect(content().string(jsonString));

        verify(venderService, times(1)).insertVender(vender);
    }

    @Test
    public void testDeleteVender() throws Exception {

        /* arrange */
        Vender vender = new Vender();
        vender.setVenderId(1);
        vender.setVenderName("vender name1");

        when(venderService.findVenderById(vender.getVenderId())).thenReturn(vender);

        /* act & assert */
        mockMvc.perform (
             delete("/admin/vender/{venderId}", vender.getVenderId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted());

        verify(venderService, times(1)).findVenderById(vender.getVenderId());
        verify(venderService, times(1)).deleteVender(vender.getVenderId());
    }

    @Test
    public void testDeleteVenderFail404NotFound() throws Exception {

        /* arrange */
        Vender vender = new Vender();
        vender.setVenderId(Integer.MAX_VALUE);
        vender.setVenderName("vender name1");

        when(venderService.findVenderById(vender.getVenderId())).thenThrow(new ResourceNotFoundException());

        /* act & assert */
        mockMvc.perform (
             delete("/admin/vender/{venderId}", vender.getVenderId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(venderService, times(1)).findVenderById(vender.getVenderId());
    }

    @Test
    public void testUpdateVender() throws Exception {

        /* arrange */
        Vender vender = new Vender();
        vender.setVenderId(1);
        vender.setVenderName("vender name1");

        when(venderService.findVenderById(vender.getVenderId())).thenReturn(vender);

        Vender updated = new Vender();
        updated.setVenderId(1);
        updated.setVenderName("updated vender name");

        String jsonString = objectMapper.writeValueAsString(updated);

        /* act & assert */
        mockMvc.perform (
             put("/admin/vender/{venderId}", vender.getVenderId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(jsonString));

        verify(venderService, times(1)).findVenderById(updated.getVenderId());
        verify(venderService, times(1)).updateVender(updated);
    }

    @Test
    public void testUpdateVenderFail404NotFound() throws Exception {

        /* arrange */
        Vender vender = new Vender();
        vender.setVenderId(Integer.MAX_VALUE);
        vender.setVenderName("vender name1");

        when(venderService.findVenderById(vender.getVenderId())).thenThrow(new ResourceNotFoundException());

        /* act & assert */
        mockMvc.perform (
             put("/admin/vender/{venderId}", vender.getVenderId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(vender)))
            .andExpect(status().isNotFound());
            

        verify(venderService, times(1)).findVenderById(vender.getVenderId());
    }
}