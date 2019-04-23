package com.espid.estimate.admin.tool.mapper;

import java.util.List;
import java.util.Optional;

import com.espid.estimate.admin.tool.model.Tool;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ToolMapper {
    public List<Tool> selectTools(@Param("search") String search);

    public Optional<Tool> findToolById(int toolId);

    public int insertTool(Tool tool);

    public int deleteTool(int toolId);

    public int updateTool(Tool tool);
}