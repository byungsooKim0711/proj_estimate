package com.espid.estimate.unittests.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import com.espid.estimate.admin.sender.mapper.SenderMapper;
import com.espid.estimate.admin.sender.model.Sender;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MybatisTest
public class SenderRepositoryTests {

    @Autowired
    private SenderMapper senderMapper;

    @Test
    public void shouldFindNoSendersIfrepositoryIsEmpty() throws Exception {
        /* arrange */
        String search = null;

        /* act */
        List<Sender> senders = senderMapper.selectSenders(search);

        /* assert */
        assertThat(senders).isEmpty();
    }

    @Test
    public void shouldStoreASender() throws Exception {
        /* arrange */
        Sender sender  = new Sender();
        sender.setJob("사원");
        sender.setSpidDept("Solution 사업본부");
        sender.setSpidEmail("bskim0711@espid.com");
        sender.setSpidId(1);
        sender.setSpidName("김병수");
        sender.setSpidTel1(null);
        sender.setSpidTel2("010-4949-2891");

        /* act */
        int actual = senderMapper.insertSender(sender);

        /* assert */
        assertEquals(1, actual);
    }

    @Test
    public void conditionalSearchWithStringKimbs() throws Exception {
        /* arrange */
        Sender sender1  = new Sender();
        sender1.setJob("사원");
        sender1.setSpidDept("Solution 사업본부");
        sender1.setSpidEmail("bskim0711@espid.com");
        sender1.setSpidId(111);
        sender1.setSpidName("Kimbs");
        sender1.setSpidTel1(null);
        sender1.setSpidTel2("010-4949-2891");

        Sender sender2  = new Sender();
        sender2.setJob("Test job");
        sender2.setSpidDept("TEST");
        sender2.setSpidEmail("TestEmail@test.test");
        sender2.setSpidId(222);
        sender2.setSpidName("no-no-no");
        sender2.setSpidTel1(null);
        sender2.setSpidTel2(null);

        Sender sender3  = new Sender();
        sender3.setJob("Test job");
        sender3.setSpidDept("TEST");
        sender3.setSpidEmail("TestEmail@test.test");
        sender3.setSpidId(333);
        sender3.setSpidName("---Kimbs---");
        sender3.setSpidTel1(null);
        sender3.setSpidTel2(null);

        senderMapper.insertSender(sender1);
        senderMapper.insertSender(sender2);
        senderMapper.insertSender(sender3);

        /* act */
        List<Sender> senders = senderMapper.selectSenders("Kimbs");
        
        /* assert */
        assertThat(senders).hasSize(2).contains(sender1, sender3);
    }

    @Test
    public void shouldFindSenderById() throws Exception {
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
        sender2.setJob("테스트");
        sender2.setSpidDept("테스트");
        sender2.setSpidEmail("test@test.test");
        sender2.setSpidId(2);
        sender2.setSpidName("테스트");
        sender2.setSpidTel1(null);
        sender2.setSpidTel2(null);

        senderMapper.insertSender(sender1);
        senderMapper.insertSender(sender2);

        /* act */
        Sender found = senderMapper.findSenderById(1).get();

        /* assert */
        assertEquals(sender1, found);
    }

    @Test
    public void shouldFindAllSenders() throws Exception {
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
        sender2.setJob("테스트");
        sender2.setSpidDept("테스트");
        sender2.setSpidEmail("test@test.test");
        sender2.setSpidId(2);
        sender2.setSpidName("테스트");
        sender2.setSpidTel1(null);
        sender2.setSpidTel2(null);

        senderMapper.insertSender(sender1);
        senderMapper.insertSender(sender2);

        /* act */
        List<Sender> senders = senderMapper.selectSenders(null);

        /* assert */
        assertThat(senders).hasSize(2).contains(sender1, sender2);
    }

    @Test
    public void shouldChangedSenderInfomation() throws Exception {
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
        sender2.setJob("테스트");
        sender2.setSpidDept("테스트");
        sender2.setSpidEmail("test@test.test");
        sender2.setSpidId(2);
        sender2.setSpidName("테스트");
        sender2.setSpidTel1(null);
        sender2.setSpidTel2(null);

        senderMapper.insertSender(sender1);
        senderMapper.insertSender(sender2);

        sender2.setSpidTel1("02-0000-0000");
        sender2.setSpidTel2("010-0000-0000");

        /* act */
        int actual = senderMapper.updateSender(sender2);

        /* assert */
        assertEquals(1, actual);

        Sender found = senderMapper.findSenderById(2).get();
        assertEquals(sender2, found);
    }

    @Test
    public void whenTheSenderIsDeleted() throws Exception {
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
        sender2.setJob("테스트");
        sender2.setSpidDept("테스트");
        sender2.setSpidEmail("test@test.test");
        sender2.setSpidId(2);
        sender2.setSpidName("테스트");
        sender2.setSpidTel1(null);
        sender2.setSpidTel2(null);

        senderMapper.insertSender(sender1);
        senderMapper.insertSender(sender2);

        /* act */
        int actual = senderMapper.deleteSender(2);

        /* assert */
        assertEquals(1, actual);

        List<Sender> senders = senderMapper.selectSenders(null);
        assertThat(senders).hasSize(1).contains(sender1);
    }
}