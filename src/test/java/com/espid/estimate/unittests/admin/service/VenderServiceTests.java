package com.espid.estimate.unittests.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.espid.estimate.admin.vender.mapper.VenderMapper;
import com.espid.estimate.admin.vender.model.Vender;
import com.espid.estimate.admin.vender.service.VenderService;
import com.espid.estimate.exception.ResourceNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class VenderServiceTests {

    @Mock
    private VenderMapper venderMapper;

    @InjectMocks
    private VenderService venderService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSelectVenders() throws Exception {

        /* arrange */
        List<Vender> venders = new ArrayList<>();
        Vender vender = new Vender();
        vender.setVenderId(1);
        vender.setVenderName("vender name");

        venders.add(vender);

        when(venderMapper.selectVenders(null)).thenReturn(venders);

        /* act */
        List<Vender> response = venderService.selectVenders(null);

        /* assert */
        assertThat(response).hasSize(1).allSatisfy(p -> assertThat(p).isEqualTo(vender));
    }

    @Test
    public void testConditionalSelectVenders() throws Exception {
        
        /* arrange*/
        List<Vender> venders = new ArrayList<>();
        Vender vender1  = new Vender();
        vender1.setVenderId(1);
        vender1.setVenderName("kimbs");

        Vender vender2  = new Vender();
        vender2.setVenderId(2);
        vender2.setVenderName("vender name2");

        venders.add(vender1);
        venders.add(vender2);

        List<Vender> conditionalVenders = new ArrayList<>();
        conditionalVenders.add(vender1);

        when(venderMapper.selectVenders(null)).thenReturn(venders);
        when(venderMapper.selectVenders("bs")).thenReturn(conditionalVenders);

        /* act */
        List<Vender> response1 = venderService.selectVenders(null);
        List<Vender> response2 = venderService.selectVenders("bs");

        /* assert */
        assertThat(response1).hasSize(2).contains(vender1, vender2);
        assertThat(response2).hasSize(1).contains(vender1);
        
    }

    @Test
    public void testFindVenderById() throws Exception {

        /* arrange */
        Vender vender = new Vender();
        vender.setVenderId(1);
        vender.setVenderName("vender name");

        when(venderMapper.findVenderById(1)).thenReturn(Optional.of(vender));
        
        /* act */
        Vender response = venderService.findVenderById(vender.getVenderId());

        /* assert */
        assertEquals(vender, response);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testFindVenderByIdWithThrowException() throws Exception {

        /* arrange */
        Vender vender = new Vender();
        vender.setVenderId(1);
        vender.setVenderName("vender name");

        when(venderMapper.findVenderById(123456)).thenReturn(Optional.of(vender));
        
        /* act */
        Vender response = venderService.findVenderById(vender.getVenderId());

        /* assert */
        assertEquals(vender, response);
    }

    @Test
    public void testInsertVender() throws Exception {

        /* arrange */
        Vender vender = new Vender();
        vender.setVenderId(1);
        vender.setVenderName("vender name");

        when(venderMapper.insertVender(vender)).thenReturn(1);

        /* act */
        Vender response = venderService.insertVender(vender);

        /* assert */
        assertEquals(vender, response);
    }

    @Test
    public void testDeleteVender() throws Exception {

        /* arrange */
        when(venderMapper.deleteVender(1234)).thenReturn(1);

        /* act */
        int response = venderService.deleteVender(1234);

        /* assert */         
        assertEquals(1, response);
    }

    @Test
    public void testUpdateVender() throws Exception {

        /* arrange */
        Vender vender = new Vender();
        vender.setVenderId(1);
        vender.setVenderName("vender name");

        when(venderMapper.insertVender(vender)).thenReturn(1);

        vender.setVenderName("updated vender name");
        when(venderMapper.updateVender(vender)).thenReturn(1);

        /* act */
        int response = venderService.updateVender(vender);

        /* assert */
        assertEquals(1, response);
    }
}