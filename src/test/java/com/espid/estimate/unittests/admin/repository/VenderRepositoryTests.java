package com.espid.estimate.unittests.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import com.espid.estimate.admin.sender.mapper.SenderMapper;
import com.espid.estimate.admin.sender.model.Sender;
import com.espid.estimate.admin.vender.mapper.VenderMapper;
import com.espid.estimate.admin.vender.model.Vender;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MybatisTest
public class VenderRepositoryTests {
    
    @Autowired
    private VenderMapper venderMapper;

    @Autowired
    private SenderMapper senderMapper;

    private Sender sender;

    @Before
    public void setup() throws Exception {
        sender  = new Sender();
        sender.setJob("사원");
        sender.setSpidDept("Solution 사업본부");
        sender.setSpidEmail("bskim0711@espid.com");
        sender.setSpidId(1);
        sender.setSpidName("김병수");
        sender.setSpidTel1(null);
        sender.setSpidTel2("010-4949-2891");

        senderMapper.insertSender(sender);
    }

    @Test
    public void shouldFindNoVendersIfrepositoryIsEmpty() throws Exception {
        /* arrange */
        String search = null;

        /* act */
        List<Vender> venders = venderMapper.selectVenders(search);

        /* assert */
        assertThat(venders).isEmpty();
    }

    @Test
    public void shouldStoreAVender() throws Exception {
        /* arrange */
        Vender vender  = new Vender();
        vender.setVenderId(1234);
        vender.setVenderName("vender name");
        vender.setSpidId(1);

        /* act */
        int actual = venderMapper.insertVender(vender);

        /* assert */
        assertEquals(1, actual);
    }

    @Test
    public void conditionalSearchWithStringKimbs() throws Exception {
        /* arrange */
        Vender vender1 = new Vender();
        vender1.setVenderId(3333);
        vender1.setVenderName("vender name1");
        vender1.setSpidId(1);

        Vender vender2 = new Vender();
        vender2.setVenderId(4444);
        vender2.setVenderName("vender name2");
        vender2.setSpidId(1);

        Vender vender3 = new Vender();
        vender3.setVenderId(5555);
        vender3.setVenderName("Kimbs");
        vender3.setSpidId(1);

        vender3.setJob(sender.getJob());
        vender3.setSpidDept(sender.getSpidDept());
        vender3.setSpidEmail(sender.getSpidEmail());
        vender3.setSpidId(sender.getSpidId());
        vender3.setSpidName(sender.getSpidName());
        vender3.setSpidTel1(sender.getSpidTel1());
        vender3.setSpidTel2(sender.getSpidTel2());

        venderMapper.insertVender(vender1);
        venderMapper.insertVender(vender2);
        venderMapper.insertVender(vender3);

        /* act */
        List<Vender> venders = venderMapper.selectVenders("Kimbs");
        
        /* assert */
        assertThat(venders).hasSize(1).contains(vender3);
    }

    @Test
    public void shouldFindVenderById() throws Exception {
        /* arrange */
        Vender vender1 = new Vender();
        vender1.setVenderId(3333);
        vender1.setVenderName("vender name1");
        vender1.setSpidId(1);

        Vender vender2 = new Vender();
        vender2.setVenderId(5678);
        vender2.setVenderName("vender name2");
        vender2.setSpidId(1);

        venderMapper.insertVender(vender1);
        venderMapper.insertVender(vender2);

        /* act */
        Vender found = venderMapper.findVenderById(5678).get();

        /* assert */
        assertEquals(vender2, found);
    }

    @Test
    public void shouldFindAllVenders() throws Exception {
        /* arrange */
        Vender vender1 = new Vender();
        vender1.setVenderId(12);
        vender1.setVenderName("vender name1");
        vender1.setSpidId(1);

        vender1.setJob(sender.getJob());
        vender1.setSpidDept(sender.getSpidDept());
        vender1.setSpidEmail(sender.getSpidEmail());
        vender1.setSpidId(sender.getSpidId());
        vender1.setSpidName(sender.getSpidName());
        vender1.setSpidTel1(sender.getSpidTel1());
        vender1.setSpidTel2(sender.getSpidTel2());

        Vender vender2 = new Vender();
        vender2.setVenderId(34);
        vender2.setVenderName("vender name2");
        vender2.setSpidId(1);

        vender2.setJob(sender.getJob());
        vender2.setSpidDept(sender.getSpidDept());
        vender2.setSpidEmail(sender.getSpidEmail());
        vender2.setSpidId(sender.getSpidId());
        vender2.setSpidName(sender.getSpidName());
        vender2.setSpidTel1(sender.getSpidTel1());
        vender2.setSpidTel2(sender.getSpidTel2());

        venderMapper.insertVender(vender1);
        venderMapper.insertVender(vender2);

        /* act */
        List<Vender> venders = venderMapper.selectVenders(null);

        /* assert */
        assertThat(venders).hasSize(2).contains(vender1, vender2);
    }

    @Test
    public void shouldChangedVenderInfomation() throws Exception {
        /* arrange */
        Vender vender1 = new Vender();
        vender1.setVenderId(1111);
        vender1.setVenderName("vender name1");
        vender1.setSpidId(1);

        Vender vender2 = new Vender();
        vender2.setVenderId(2222);
        vender2.setVenderName("vender name2");
        vender2.setSpidId(1);

        venderMapper.insertVender(vender1);
        venderMapper.insertVender(vender2);

        /* act */
        int actual = venderMapper.updateVender(vender2);

        /* assert */
        assertEquals(1, actual);

        Vender found = venderMapper.findVenderById(2222).get();
        assertEquals(vender2, found);
    }

    @Test
    public void whenTheVenderIsDeleted() throws Exception {
        /* arrange */
        Vender vender1 = new Vender();
        vender1.setVenderId(1111);
        vender1.setVenderName("vender name1");
        vender1.setSpidId(1);

        vender1.setJob(sender.getJob());
        vender1.setSpidDept(sender.getSpidDept());
        vender1.setSpidEmail(sender.getSpidEmail());
        vender1.setSpidId(sender.getSpidId());
        vender1.setSpidName(sender.getSpidName());
        vender1.setSpidTel1(sender.getSpidTel1());
        vender1.setSpidTel2(sender.getSpidTel2());

        Vender vender2 = new Vender();
        vender2.setVenderId(2222);
        vender2.setVenderName("vender name2");
        vender2.setSpidId(1);

        vender2.setJob(sender.getJob());
        vender2.setSpidDept(sender.getSpidDept());
        vender2.setSpidEmail(sender.getSpidEmail());
        vender2.setSpidId(sender.getSpidId());
        vender2.setSpidName(sender.getSpidName());
        vender2.setSpidTel1(sender.getSpidTel1());
        vender2.setSpidTel2(sender.getSpidTel2());

        venderMapper.insertVender(vender1);
        venderMapper.insertVender(vender2);

        /* act */
        int actual = venderMapper.deleteVender(2222);

        /* assert */
        assertEquals(1, actual);

        List<Vender> venders = venderMapper.selectVenders(null);
        assertThat(venders).hasSize(1).allSatisfy(v -> assertThat(v).isEqualTo(vender1));
    }
}