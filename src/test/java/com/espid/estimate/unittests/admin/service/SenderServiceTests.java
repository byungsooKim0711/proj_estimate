package com.espid.estimate.unittests.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.espid.estimate.admin.sender.mapper.SenderMapper;
import com.espid.estimate.admin.sender.model.Sender;
import com.espid.estimate.admin.sender.service.SenderService;
import com.espid.estimate.exception.ResourceNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SenderServiceTests {

    @Mock
    private SenderMapper senderMapper;

    @InjectMocks
    private SenderService senderService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSelectSenders() throws Exception {
        
        /* arrange*/
        List<Sender> senders = new ArrayList<>();
        Sender sender  = new Sender();
        sender.setJob("사원");
        sender.setSpidDept("Solution 사업본부");
        sender.setSpidEmail("bskim0711@espid.com");
        sender.setSpidId(1);
        sender.setSpidName("김병수");
        sender.setSpidTel1(null);
        sender.setSpidTel2("010-4949-2891");

        senders.add(sender);

        when(senderMapper.selectSenders(null)).thenReturn(senders);

        /* act */
        List<Sender> response = senderService.selectSenders(null);

        /* assert */
        assertThat(response).hasSize(1).allSatisfy(p -> assertThat(p).isEqualTo(sender));
    }

    @Test
    public void testConditionalSelectSenders() throws Exception {
        
        /* arrange*/
        List<Sender> senders = new ArrayList<>();
        Sender sender1  = new Sender();
        sender1.setJob("사원");
        sender1.setSpidDept("Solution 사업본부");
        sender1.setSpidEmail("bskim0711@espid.com");
        sender1.setSpidId(1);
        sender1.setSpidName("Kimbs");
        sender1.setSpidTel1(null);
        sender1.setSpidTel2("010-4949-2891");

        Sender sender2  = new Sender();
        sender2.setJob("test");
        sender2.setSpidDept("test");
        sender2.setSpidEmail("test");
        sender2.setSpidId(2);
        sender2.setSpidName("test");
        sender2.setSpidTel1("test");
        sender2.setSpidTel2("test");

        senders.add(sender1);
        senders.add(sender2);

        List<Sender> conditionalSenders = new ArrayList<>();
        conditionalSenders.add(sender1);

        when(senderMapper.selectSenders(null)).thenReturn(senders);
        when(senderMapper.selectSenders("bs")).thenReturn(conditionalSenders);

        /* act */
        List<Sender> response1 = senderService.selectSenders("bs");
        List<Sender> response2 = senderService.selectSenders(null);

        /* assert */
        assertThat(response1).hasSize(1).contains(sender1);
        assertThat(response2).hasSize(2).contains(sender1, sender2);
        
    }

    @Test
    public void testFindSenderById() throws Exception {

        /* arrange */
        Sender sender = new Sender();
        sender.setJob("사원");
        sender.setSpidDept("Solution 사업본부");
        sender.setSpidEmail("bskim0711@espid.com");
        sender.setSpidId(1234);
        sender.setSpidName("Kimbs");
        sender.setSpidTel1(null);
        sender.setSpidTel2("010-4949-2891");

        when(senderMapper.findSenderById(1234)).thenReturn(Optional.of(sender));

        /* act */
        Sender response = senderService.findSenderById(1234);

        /* assert */
        assertEquals(sender, response);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testFindSenderByIdWithThrowException() throws Exception {

        /* arrange */
        Sender sender = new Sender();
        sender.setJob("사원");
        sender.setSpidDept("Solution 사업본부");
        sender.setSpidEmail("bskim0711@espid.com");
        sender.setSpidId(1);
        sender.setSpidName("Kimbs");
        sender.setSpidTel1(null);
        sender.setSpidTel2("010-4949-2891");

        when(senderMapper.findSenderById(1111)).thenReturn(Optional.of(sender));

        /* act */
        /* expected throw ResourceNotFoundException */
        senderService.findSenderById(2222);

        /* assert */
    }

    @Test
    public void testInsertSender() throws Exception {

        /* arrange */
        Sender sender = new Sender();
        sender.setJob("사원");
        sender.setSpidDept("Solution 사업본부");
        sender.setSpidEmail("bskim0711@espid.com");
        sender.setSpidId(1);
        sender.setSpidName("Kimbs");
        sender.setSpidTel1(null);
        sender.setSpidTel2("010-4949-2891");

        when(senderMapper.insertSender(sender)).thenReturn(1);

        /* act */
        Sender response = senderService.insertSender(sender);

        /* assert */
        assertEquals(sender, response);
    }

    @Test
    public void testDeleteSender() throws Exception {

        /* arrange */
        when(senderMapper.deleteSender(1234)).thenReturn(1);

        /* act */
        int response = senderService.deleteSender(1234);

        /* assert */         
        assertEquals(1, response);
    }

    @Test
    public void testUpdateSender() throws Exception {

        /* arrange */
        Sender sender = new Sender();
        sender.setJob("사원");
        sender.setSpidDept("Solution 사업본부");
        sender.setSpidEmail("bskim0711@espid.com");
        sender.setSpidId(1);
        sender.setSpidName("Kimbs");
        sender.setSpidTel1(null);
        sender.setSpidTel2("010-4949-2891");

        when(senderMapper.insertSender(sender)).thenReturn(1);

        sender.setSpidTel1("010-0000-0000");
        when(senderMapper.updateSender(sender)).thenReturn(1);

        /* act */
        int response = senderService.updateSender(sender);

        /* assert */
        assertEquals(1, response);
    }
}