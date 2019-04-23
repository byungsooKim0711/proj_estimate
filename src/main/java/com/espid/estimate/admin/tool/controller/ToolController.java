package com.espid.estimate.admin.tool.controller;

import java.util.List;

import com.espid.estimate.admin.tool.model.Tool;
import com.espid.estimate.admin.tool.service.ToolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/admin")
public class ToolController {

    @Autowired
    private ToolService toolService;

    @GetMapping("/tool")
    public List<Tool> getTools(@RequestParam(name = "search", required = false) String search) throws Exception {
        return toolService.selectTools(search);
    }

    @PostMapping("/tool")
    public ResponseEntity<Tool> insertTool(@RequestBody final Tool tool, final UriComponentsBuilder uriBuilder) throws Exception {
        toolService.insertTool(tool);

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/admin/tool/{id}").buildAndExpand(tool.getToolId()).toUri());

        return new ResponseEntity<>(tool, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/tool/{toolId}")
    public ResponseEntity<Void> deleteTool(@PathVariable(name = "toolId") int toolId) throws Exception {
        Tool deleted = toolService.findToolById(toolId);

        toolService.deleteTool(deleted.getToolId());
        
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/tool/{toolId}")
    public ResponseEntity<Tool> updateTool(@PathVariable(name = "toolId") int toolId, @RequestBody final Tool tool) throws Exception {
        Tool updated = toolService.findToolById(toolId);

        tool.setToolId(updated.getToolId());
        toolService.updateTool(tool);
        
        return new ResponseEntity<>(tool, HttpStatus.OK);
    }
}