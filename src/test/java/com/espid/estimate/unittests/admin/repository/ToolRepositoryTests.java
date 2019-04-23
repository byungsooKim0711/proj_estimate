package com.espid.estimate.unittests.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import com.espid.estimate.admin.tool.mapper.ToolMapper;
import com.espid.estimate.admin.tool.model.Tool;
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
public class ToolRepositoryTests {

    @Autowired
    private ToolMapper toolMapper;

    @Autowired
    private VenderMapper venderMapper;

    private Vender vender;

    @Before
    public void setup() {
        vender  = new Vender();
        vender.setVenderId(1234);
        vender.setVenderName("setup vender name");
        /* vender.setSpidId(1); */ /* 외래키 때문에 설정 No No!! */

        venderMapper.insertVender(vender);
    }

    @Test
    public void shouldFindNoToolsIfrepositoryIsEmpty() throws Exception {
        /* arrange */
        String search = null;

        /* act */
        List<Tool> tools = toolMapper.selectTools(search);

        /* assert */
        assertThat(tools).isEmpty();
    }

    @Test
    public void shouldStoreATool() throws Exception {
        /* arrange */
        Tool tool  = new Tool();
        tool.setToolId(1111);
        tool.setToolName("tool name");
        tool.setVenderId(1234);

        /* act */
        int actual = toolMapper.insertTool(tool);

        /* assert */
        assertEquals(1, actual);
    }

    @Test
    public void conditionalSearchWithStringKimbs() throws Exception {
        /* arrange */
        Tool tool1 = new Tool();
        tool1.setToolId(111);
        tool1.setToolName("tool name1");
        tool1.setVenderId(1234);

        Tool tool2 = new Tool();
        tool2.setToolId(222);
        tool2.setToolName("tool name2");
        tool2.setVenderId(1234);

        Tool tool3 = new Tool();
        tool3.setToolId(333);
        tool3.setToolName("--Kimbs--");
        tool3.setVenderId(1234);

        tool3.setVenderName(vender.getVenderName());

        toolMapper.insertTool(tool1);
        toolMapper.insertTool(tool2);
        toolMapper.insertTool(tool3);

        /* act */
        List<Tool> tools = toolMapper.selectTools("Kimbs");
        
        /* assert */
        assertThat(tools).hasSize(1).contains(tool3);
    }

    @Test
    public void shouldFindToolById() throws Exception {
        /* arrange */
        Tool tool1 = new Tool();
        tool1.setToolId(3333);
        tool1.setToolName("tool name1");
        tool1.setVenderId(1234);

        Tool tool2 = new Tool();
        tool2.setToolId(5678);
        tool2.setToolName("tool name2");
        tool2.setVenderId(1234);

        toolMapper.insertTool(tool1);
        toolMapper.insertTool(tool2);

        /* act */
        Tool found = toolMapper.findToolById(3333).get();

        /* assert */
        assertEquals(tool1, found);
    }

    @Test
    public void shouldFindAllTools() throws Exception {
        /* arrange */
        Tool tool1 = new Tool();
        tool1.setToolId(123456789);
        tool1.setToolName("tool name1");
        tool1.setVenderId(1234);

        tool1.setVenderName(vender.getVenderName());

        Tool tool2 = new Tool();
        tool2.setToolId(3434);
        tool2.setToolName("tool name2");
        tool2.setVenderId(1234);

        tool2.setVenderName(vender.getVenderName());

        toolMapper.insertTool(tool1);
        toolMapper.insertTool(tool2);

        /* act */
        List<Tool> tools = toolMapper.selectTools(null);

        /* assert */
        assertThat(tools).hasSize(2).contains(tool1, tool2);
    }

    @Test
    public void shouldChangedToolInfomation() throws Exception {
        /* arrange */
        Tool tool1 = new Tool();
        tool1.setToolId(8888);
        tool1.setToolName("tool name1");
        tool1.setVenderId(1234);

        Tool tool2 = new Tool();
        tool2.setToolId(123456789);
        tool2.setToolName("tool name2");
        tool2.setVenderId(1234);
        
        toolMapper.insertTool(tool1);
        toolMapper.insertTool(tool2);
        
        /* act */
        int actual = toolMapper.updateTool(tool2);

        /* assert */
        assertEquals(1, actual);

        Tool found = toolMapper.findToolById(123456789).get();
        assertEquals(tool2, found);
    }

    @Test
    public void whenTheToolIsDeleted() throws Exception {
        /* arrange */
        Tool tool1 = new Tool();
        tool1.setToolId(13579);
        tool1.setToolName("tool name1");
        tool1.setVenderId(1234);

        tool1.setVenderName(vender.getVenderName());

        Tool tool2 = new Tool();
        tool2.setToolId(24680);
        tool2.setToolName("tool name2");
        tool2.setVenderId(1234);

        tool2.setVenderName(vender.getVenderName());

        toolMapper.insertTool(tool1);
        toolMapper.insertTool(tool2);

        /* act */
        int actual = toolMapper.deleteTool(24680);

        /* assert */
        assertEquals(1, actual);

        List<Tool> tools = toolMapper.selectTools(null);
        assertThat(tools).hasSize(1).contains(tool1);
    }
}