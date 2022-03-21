package com.example.agileboardsserver.controller;

import com.example.agileboardsserver.dto.CreateProject;
import com.example.agileboardsserver.dto.ProjectDto;
import com.example.agileboardsserver.model.Project;
import com.example.agileboardsserver.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjectsForUser() {
        return new ResponseEntity<>(projectService.getAllProjects(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getById(@PathVariable String id) {
        return new ResponseEntity<>(projectService.getById(id), OK);
    }

    @PostMapping
    public ResponseEntity<ProjectDto> addProject(@RequestBody CreateProject project) {
        return new ResponseEntity<>(projectService.createProject(project), OK);
    }

    @DeleteMapping("/{id}")
    public void removeProject(@PathVariable String id) {
        projectService.deleteProject(id);
    }

    @PutMapping
    public ResponseEntity<ProjectDto> updateProject(@RequestBody ProjectDto project) {
        return new ResponseEntity<>(projectService.update(project), OK);
    }
}
