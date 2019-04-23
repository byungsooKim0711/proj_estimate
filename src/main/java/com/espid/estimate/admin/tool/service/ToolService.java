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

    public List<Tool> selectTools(String search) {
        return toolMapper.selectTools(search);
    }

    /**
     * 
     * @param toolId
     * @return Tool
     * @throws ResourceNotFoundException
      */
    public Tool findToolById(int toolId) {
        return toolMapper.findToolById(toolId).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    public Tool insertTool(Tool tool) {
        toolMapper.insertTool(tool);
        return tool;
    }

    public int deleteTool(int toolId) {
        return toolMapper.deleteTool(toolId);
    }

    public int updateTool(Tool tool) {
        return toolMapper.updateTool(tool);
    }
}