package com.example.agileboardsserver.controller;

import com.example.agileboardsserver.dto.ProjectDto;
import com.example.agileboardsserver.model.Project;
import com.example.agileboardsserver.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> addProject(@RequestBody ProjectDto project) {
        return new ResponseEntity<>(projectService.createProject(project), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public void removeProject(@PathVariable String id) {
        projectService.deleteProject(id);
    }
}
