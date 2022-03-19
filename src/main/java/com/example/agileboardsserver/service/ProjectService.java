package com.example.agileboardsserver.service;

import com.example.agileboardsserver.dto.CreateProject;
import com.example.agileboardsserver.model.Project;
import com.example.agileboardsserver.model.User;
import com.example.agileboardsserver.repository.ProjectRepository;
import com.example.agileboardsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Project createProject(CreateProject newProject) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        Project project = new Project();
        project.setName(newProject.getName());
        project.setDescription(newProject.getDescription());
        project.setCreatedAt(LocalDateTime.now());
        project.setUser(user);

        return projectRepository.save(project);
    }

    public void deleteProject(String id) {
        // TODO: Check if user with given username can delete this
        projectRepository.deleteById(UUID.fromString(id));
    }

    public List<Project> getAllProjects() {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        return projectRepository.findByUser(user);
    }

    public Project getById(String id) {
        return projectRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Transactional
    public Integer updateNameById(String id, String name) {
        return projectRepository.updateNameById(UUID.fromString(id), name);
    }

    @Transactional
    public Integer updateDescriptionById(String id, String description) {
        return projectRepository.updateDescriptionById(UUID.fromString(id), description);
    }

}
