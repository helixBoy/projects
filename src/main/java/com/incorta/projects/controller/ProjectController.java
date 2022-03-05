package com.incorta.projects.controller;

import com.incorta.projects.dto.ProjectDto;
import com.incorta.projects.dto.ProjectParticipantDto;
import com.incorta.projects.entity.Project;
import com.incorta.projects.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping
    public ResponseEntity<Project> saveProject(@RequestBody ProjectDto projectDto) {
        return new ResponseEntity<Project>(projectService.saveProject(projectDto), HttpStatus.CREATED);
    }

    @PostMapping("assign")
    public ResponseEntity<Map<String,String>> assignParticipants(@RequestBody ProjectParticipantDto projectParticipantDto) {
        Map<String,String> response = new HashMap<>();
        projectService.assignParticipants(projectParticipantDto);
        response.put("status", "Success");
        response.put("message", "The participant has been assigned to the project successfully");
        return ResponseEntity.accepted().body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Project> updateProject(@PathVariable long id, @RequestBody ProjectDto projectDto) {
        return new ResponseEntity<Project>(projectService.updateProject(id, projectDto), HttpStatus.CREATED);
    }
}
