package com.espid.estimate.unittests.admin.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.espid.estimate.admin.tool.controller.ToolController;
import com.espid.estimate.admin.tool.model.Tool;
import com.espid.estimate.admin.tool.service.ToolService;
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

public class ToolControllerTests {

    @InjectMocks
    private ToolController toolController;

    @Mock
    private ToolService toolService;

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
            .standaloneSetup(toolController)
            .build();
    }

    @Test
    public void testGetTools() throws Exception {
        
        /* arrange */
        Tool tool = new Tool();
        tool.setToolId(1);
        tool.setToolName("name");
        tool.setToolDetails("details");
        tool.setToolLicense("license");
        tool.setToolModelNumber("model number");
        
        List<Tool> tools = Arrays.asList(tool);

        when(toolService.selectTools(null)).thenReturn(tools);

        String jsonString = objectMapper.writeValueAsString(tools);

        /* act & assert */
        mockMvc.perform (
             get("/admin/tool"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(jsonString));

        verify(toolService, times(1)).selectTools(null);
    }

    @Test
    public void testConditionalGetTools() throws Exception {

        /* arrange */
        Tool tool = new Tool();
        tool.setToolId(1);
        tool.setToolName("kimbs");
        tool.setToolDetails("details");
        tool.setToolLicense("license");
        tool.setToolModelNumber("model number");

        List<Tool> tools = new ArrayList<>();
        tools.add(tool);

        when(toolService.selectTools("kimbs")).thenReturn(tools);

        String jsonString = objectMapper.writeValueAsString(tools);

        /* act & assert */
        mockMvc.perform (
             get("/admin/tool")
            .param("search", "kimbs"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(jsonString));

        verify(toolService, times(1)).selectTools("kimbs");
    }

    @Test
    public void testInsertTool() throws Exception {

        /* arrange */
        Tool tool = new Tool();
        tool.setToolId(1);
        tool.setToolName("kimbs");
        tool.setToolDetails("details");
        tool.setToolLicense("license");
        tool.setToolModelNumber("model number");

        when(toolService.insertTool(tool)).thenReturn(tool);

        String jsonString = objectMapper.writeValueAsString(tool);

        /* act & assert */
        mockMvc.perform (
             post("/admin/tool")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString))
            .andExpect(status().isCreated())
            .andExpect(header().string("location", containsString("http://localhost/admin/tool/" + tool.getToolId())))
            .andExpect(content().string(equalTo(jsonString)));

    }

    @Test
    public void testDeleteTool() throws Exception {

        /* arrange */
        Tool tool = new Tool();
        tool.setToolId(1);
        tool.setToolName("kimbs");
        tool.setToolDetails("details");
        tool.setToolLicense("license");
        tool.setToolModelNumber("model number");

        when(toolService.findToolById(tool.getToolId())).thenReturn(tool);

        /* act & assert */
        mockMvc.perform (
             delete("/admin/tool/{toolId}", tool.getToolId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted());

        verify(toolService, times(1)).findToolById(tool.getToolId());
        verify(toolService, times(1)).deleteTool(tool.getToolId());
    }

    @Test
    public void testDeleteToolFail404NotFound() throws Exception {

        /* arrange */
        Tool tool = new Tool();
        tool.setToolId(Integer.MAX_VALUE);
        tool.setToolName("kimbs");
        tool.setToolDetails("details");
        tool.setToolLicense("license");
        tool.setToolModelNumber("model number");

        when(toolService.findToolById(tool.getToolId())).thenThrow(new ResourceNotFoundException());

        /* act & assert */
        mockMvc.perform (
             delete("/admin/tool/{toolId}", tool.getToolId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
            

        verify(toolService, times(1)).findToolById(tool.getToolId());
    }

    @Test
    public void testUpdateTool() throws Exception {

        /* arrange */
        Tool tool = new Tool();
        tool.setToolId(1);
        tool.setToolName("name");
        tool.setToolDetails("details");
        tool.setToolLicense("license");
        tool.setToolModelNumber("model number");

        when(toolService.findToolById(tool.getToolId())).thenReturn(tool);

        Tool updated = new Tool();
        updated.setToolId(tool.getToolId());
        updated.setToolName("kimbs");
        updated.setToolDetails("kimbs");
        updated.setToolLicense("kimbs");
        updated.setToolModelNumber("kimbs");

        String jsonString = objectMapper.writeValueAsString(updated);

        /* act & assert */
        mockMvc.perform (
             put("/admin/tool/{toolId}", tool.getToolId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updated)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(jsonString));

        verify(toolService, times(1)).findToolById(updated.getToolId());
        verify(toolService, times(1)).updateTool(updated);
    }

    @Test
    public void testUpdateToolFail404NotFound() throws Exception {

        /* arrange */
        Tool tool = new Tool();
        tool.setToolId(Integer.MAX_VALUE);
        tool.setToolName("name");
        tool.setToolDetails("details");
        tool.setToolLicense("license");
        tool.setToolModelNumber("model number");

        when(toolService.findToolById(tool.getToolId())).thenThrow(new ResourceNotFoundException());

        /* act & assert */
        mockMvc.perform (
             put("/admin/tool/{toolId}", tool.getToolId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tool)))
            .andExpect(status().isNotFound());
            

        verify(toolService, times(1)).findToolById(tool.getToolId());
    }
}