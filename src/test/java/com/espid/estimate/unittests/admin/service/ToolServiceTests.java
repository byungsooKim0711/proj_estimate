package com.espid.estimate.unittests.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.espid.estimate.admin.tool.mapper.ToolMapper;
import com.espid.estimate.admin.tool.model.Tool;
import com.espid.estimate.admin.tool.service.ToolService;
import com.espid.estimate.exception.ResourceNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ToolServiceTests {

    @Mock
    private ToolMapper toolMapper;

    @InjectMocks
    private ToolService toolService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSelectTools() throws Exception {

        /* arrange */
        List<Tool> tools = new ArrayList<>();
        Tool tool = new Tool();
        tool.setToolId(1);
        tool.setToolName("tool name");
        tool.setToolLicense("tool license");
        tool.setToolModelNumber("tool model number");
        tool.setToolDetails("tool details");

        tools.add(tool);

        when(toolMapper.selectTools(null)).thenReturn(tools);

        /* act */
        List<Tool> response = toolService.selectTools(null);

        /* assert */
        assertThat(response).hasSize(1).allSatisfy(p -> assertThat(p).isEqualTo(tool));
    }

    @Test
    public void testConditionalSelectTools() throws Exception {
        
        /* arrange*/
        List<Tool> tools = new ArrayList<>();
        Tool tool1 = new Tool();
        tool1.setToolId(1);
        tool1.setToolName("tool name");
        tool1.setToolLicense("tool license");
        tool1.setToolModelNumber("tool model number");
        tool1.setToolDetails("tool details");

        Tool tool2 = new Tool();
        tool2.setToolId(2);
        tool2.setToolName("kimbs");
        tool2.setToolLicense("tool license");
        tool2.setToolModelNumber("tool model number");
        tool2.setToolDetails("tool details");

        tools.add(tool1);
        tools.add(tool2);

        List<Tool> conditionalTools = new ArrayList<>();
        conditionalTools.add(tool2);

        when(toolMapper.selectTools(null)).thenReturn(tools);
        when(toolMapper.selectTools("bs")).thenReturn(conditionalTools);

        /* act */
        List<Tool> response1 = toolService.selectTools(null);
        List<Tool> response2 = toolService.selectTools("bs");

        /* assert */
        assertThat(response1).hasSize(2).contains(tool1, tool2);
        assertThat(response2).hasSize(1).contains(tool2);
        
    }

    @Test
    public void testFindToolById() throws Exception {

        /* arrange */
        Tool tool = new Tool();
        tool.setToolId(1);
        tool.setToolName("tool name");
        tool.setToolLicense("tool license");
        tool.setToolModelNumber("tool model number");
        tool.setToolDetails("tool details");

        when(toolMapper.findToolById(1)).thenReturn(Optional.of(tool));
        
        /* act */
        Tool response = toolService.findToolById(tool.getToolId());

        /* assert */
        assertEquals(tool, response);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testFindToolByIdWithThrowException() throws Exception {

        /* arrange */
        Tool tool = new Tool();
        tool.setToolId(1);
        tool.setToolName("tool name");
        tool.setToolLicense("tool license");
        tool.setToolModelNumber("tool model number");
        tool.setToolDetails("tool details");

        when(toolMapper.findToolById(123456)).thenReturn(Optional.of(tool));
        
        /* act */
        Tool response = toolService.findToolById(tool.getToolId());

        /* assert */
        assertEquals(tool, response);
    }

    @Test
    public void testInsertTool() throws Exception {

        /* arrange */
        Tool tool = new Tool();
        tool.setToolId(1);
        tool.setToolName("tool name");
        tool.setToolLicense("tool license");
        tool.setToolModelNumber("tool model number");
        tool.setToolDetails("tool details");

        when(toolMapper.insertTool(tool)).thenReturn(1);

        /* act */
        Tool response = toolService.insertTool(tool);

        /* assert */
        assertEquals(tool, response);
    }

    @Test
    public void testDeleteTool() throws Exception {

        /* arrange */
        when(toolMapper.deleteTool(1234)).thenReturn(1);

        /* act */
        int response = toolService.deleteTool(1234);

        /* assert */         
        assertEquals(1, response);
    }

    @Test
    public void testUpdateTool() throws Exception {

        /* arrange */
        Tool tool = new Tool();
        tool.setToolId(1);
        tool.setToolName("tool name");
        tool.setToolLicense("tool license");
        tool.setToolModelNumber("tool model number");
        tool.setToolDetails("tool details");

        when(toolMapper.insertTool(tool)).thenReturn(1);

        tool.setToolName("updated tool name");
        when(toolMapper.updateTool(tool)).thenReturn(1);

        /* act */
        int response = toolService.updateTool(tool);

        /* assert */
        assertEquals(1, response);
    }
}