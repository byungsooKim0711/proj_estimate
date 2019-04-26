package com.espid.estimate.unittests.admin.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.espid.estimate.admin.sender.model.Sender;
import com.espid.estimate.admin.sender.service.SenderService;
import com.espid.estimate.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SenderControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired 
    private ObjectMapper objectMapper;

    @MockBean
    private SenderService senderService;

    @Test
    public void testInsertSender() throws Exception {
        
        /* arrange */
        Sender sender = new Sender();
        sender.setJob("사원");
        sender.setSpidDept("Solution 사업본부");
        sender.setSpidEmail("bskim0711@espid.com");
        sender.setSpidId(1);
        sender.setSpidName("김병수");
        sender.setSpidTel1(null);
        sender.setSpidTel2("010-4949-2891");

        when(senderService.insertSender(sender)).thenReturn(sender);

        String jsonString = objectMapper.writeValueAsString(sender);

        /* act & assert */
        mockMvc.perform (
             post("/admin/sender")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("location", containsString("http://localhost/admin/sender/" + sender.getSpidId())))
            .andExpect(content().string(equalTo(jsonString))
        );
    }

    @Test
    public void testDeleteSender() throws Exception {

        /* arrange */
        Sender deleteSender = new Sender();
        deleteSender.setJob("사원");
        deleteSender.setSpidDept("Solution 사업본부");
        deleteSender.setSpidEmail("bskim0711@espid.com");
        deleteSender.setSpidId(1);
        deleteSender.setSpidName("김병수");
        deleteSender.setSpidTel1(null);
        deleteSender.setSpidTel2("010-4949-2891");

        when(senderService.findSenderById(deleteSender.getSpidId())).thenReturn(deleteSender);

        /* act & assert */
        mockMvc.perform (
             delete("/admin/sender/{senderId}", deleteSender.getSpidId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted());

        verify(senderService, times(1)).findSenderById(deleteSender.getSpidId());
        verify(senderService, times(1)).deleteSender(deleteSender.getSpidId());
    }

    @Test
    public void testDeleteSenderFail404NotFound() throws Exception {

        /* arrange */
        Sender sender = new Sender();
        sender.setJob("사원");
        sender.setSpidDept("Solution 사업본부");
        sender.setSpidEmail("bskim0711@espid.com");
        sender.setSpidId(Integer.MAX_VALUE);
        sender.setSpidName("김병수");
        sender.setSpidTel1(null);
        sender.setSpidTel2("010-4949-2891");

        when(senderService.findSenderById(sender.getSpidId())).thenThrow(new ResourceNotFoundException());

        /* act & assert */
        mockMvc.perform (
             delete("/admin/sender/{senderId}", sender.getSpidId())
             .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
            

        verify(senderService, times(1)).findSenderById(sender.getSpidId());
    }


    @Test
    public void testGetSenders() throws Exception {

        /* arrange */
        Sender sender1 = new Sender();
        sender1.setJob("사원");
        sender1.setSpidDept("Solution 사업본부");
        sender1.setSpidEmail("bskim0711@espid.com");
        sender1.setSpidId(1);
        sender1.setSpidName("김병수");
        sender1.setSpidTel1(null);
        sender1.setSpidTel2("010-4949-2891");

        Sender sender2 = new Sender();
        sender2.setJob("JOB");
        sender2.setSpidDept("DEPARTMENT");
        sender2.setSpidEmail("EMAIL");
        sender2.setSpidId(2);
        sender2.setSpidName("NAME");
        sender2.setSpidTel1("TEL1");
        sender2.setSpidTel2("TEL2");

        List<Sender> senders = new ArrayList<>();
        senders.add(sender1);
        senders.add(sender2);

        when(senderService.selectSenders(null)).thenReturn(senders);

        String jsonString = objectMapper.writeValueAsString(senders);

        /* act & assert */
        mockMvc.perform (
             get("/admin/sender"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(jsonString));

        verify(senderService, times(1)).selectSenders(null);
    }

    @Test
    public void testConditionalGetSenders() throws Exception {

        /* arrange */
        Sender sender1 = new Sender();
        sender1.setJob("사원");
        sender1.setSpidDept("Solution 사업본부");
        sender1.setSpidEmail("bskim0711@espid.com");
        sender1.setSpidId(1);
        sender1.setSpidName("김병수");
        sender1.setSpidTel1(null);
        sender1.setSpidTel2("010-4949-2891");

        Sender sender2 = new Sender();
        sender2.setJob("JOB");
        sender2.setSpidDept("DEPARTMENT");
        sender2.setSpidEmail("EMAIL");
        sender2.setSpidId(2);
        sender2.setSpidName("NAME");
        sender2.setSpidTel1("TEL1");
        sender2.setSpidTel2("TEL2");

        List<Sender> senders = new ArrayList<>();
        senders.add(sender2);

        when(senderService.selectSenders("NAME")).thenReturn(senders);

        String jsonString = objectMapper.writeValueAsString(senders);

        /* act & assert */
        mockMvc.perform (
             get("/admin/sender")
            .param("search", "NAME"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(jsonString));

        verify(senderService, times(1)).selectSenders("NAME");
    }

    @Test
    public void testUpdateSender() throws Exception {

        /* arrange */
        Sender sender = new Sender();
        sender.setJob("사원");
        sender.setSpidDept("Solution 사업본부");
        sender.setSpidEmail("bskim0711@espid.com");
        sender.setSpidId(1);
        sender.setSpidName("김병수");
        sender.setSpidTel1(null);
        sender.setSpidTel2("010-4949-2891");

        when(senderService.findSenderById(sender.getSpidId())).thenReturn(sender);

        Sender updated = senderService.findSenderById(sender.getSpidId());
        updated.setJob("updated job");
        updated.setSpidDept("updated department");
        updated.setSpidEmail("updated email");
        updated.setSpidId(1);
        updated.setSpidName("updated name");
        updated.setSpidTel1("updated tel1");
        updated.setSpidTel2("updated tel2");

        String jsonString = objectMapper.writeValueAsString(updated);

        /* act & assert */
        mockMvc.perform (
             put("/admin/sender/{senderId}", sender.getSpidId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updated)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(jsonString));

        verify(senderService, times(2)).findSenderById(updated.getSpidId());
        verify(senderService, times(1)).updateSender(updated);
    }

    @Test
    public void testUpdateSenderFail404NotFound() throws Exception {

        /* arrange */
        Sender sender = new Sender();
        sender.setJob("사원");
        sender.setSpidDept("Solution 사업본부");
        sender.setSpidEmail("bskim0711@espid.com");
        sender.setSpidId(Integer.MAX_VALUE);
        sender.setSpidName("김병수");
        sender.setSpidTel1(null);
        sender.setSpidTel2("010-4949-2891");

        when(senderService.findSenderById(sender.getSpidId())).thenThrow(new ResourceNotFoundException());

        /* act & assert */
        mockMvc.perform (
             put("/admin/sender/{senderId}", sender.getSpidId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(sender)))
            .andExpect(status().isNotFound());
            

        verify(senderService, times(1)).findSenderById(sender.getSpidId());
    }
}