package com.example.agileboardsserver.controller;

import com.example.agileboardsserver.dto.CreateProject;
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
    public ResponseEntity<List<Project>> getAllProjectsForUser() {
        return new ResponseEntity<>(projectService.getAllProjects(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getById(@PathVariable String id) {
        return new ResponseEntity<>(projectService.getById(id), OK);
    }

    @PostMapping
    public ResponseEntity<Project> addProject(@RequestBody CreateProject project) {
        return new ResponseEntity<>(projectService.createProject(project), OK);
    }

    @PostMapping("/name/{id}")
    public ResponseEntity<Integer> updateName(@PathVariable String id, @RequestBody String name) {
        return new ResponseEntity<>(projectService.updateNameById(id, name), OK);
    }

    @PostMapping("/description/{id}")
    public ResponseEntity<Integer> updateDescription(@PathVariable String id, @RequestBody String description) {
        return new ResponseEntity<>(projectService.updateDescriptionById(id, description), OK);
    }

    @DeleteMapping("/remove/{id}")
    public void removeProject(@PathVariable String id) {
        projectService.deleteProject(id);
    }
}
