package com.espid.estimate.admin.tool.mapper;

import java.util.List;
import java.util.Optional;

import com.espid.estimate.admin.tool.model.Tool;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ToolMapper {
    public List<Tool> selectTools(@Param("search") String search) throws Exception;

    public Optional<Tool> findToolById(int toolId) throws Exception;

    public int insertTool(Tool tool) throws Exception;

    public int deleteTool(int toolId) throws Exception;

    public int updateTool(Tool tool) throws Exception;
}