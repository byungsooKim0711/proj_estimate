package com.espid.estimate.admin.tool.service;

import java.util.List;

import com.espid.estimate.admin.tool.mapper.ToolMapper;
import com.espid.estimate.admin.tool.model.Tool;
import com.espid.estimate.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToolService {

    @Autowired
    private ToolMapper toolMapper;

    public List<Tool> selectTools(String search) throws Exception {
        return toolMapper.selectTools(search);
    }

    /**
     * 
     * @param toolId
     * @return Tool
     * @throws ResourceNotFoundException
      */
    public Tool findToolById(int toolId) throws Exception {
        return toolMapper.findToolById(toolId).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    public Tool insertTool(Tool tool) throws Exception {
        toolMapper.insertTool(tool);
        return tool;
    }

    public int deleteTool(int toolId) throws Exception {
        return toolMapper.deleteTool(toolId);
    }

    public int updateTool(Tool tool) throws Exception {
        return toolMapper.updateTool(tool);
    }
}