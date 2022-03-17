package com.example.agileboardsserver.service;

import com.example.agileboardsserver.dto.ProjectDto;
import com.example.agileboardsserver.model.Project;
import com.example.agileboardsserver.model.User;
import com.example.agileboardsserver.repository.ProjectRepository;
import com.example.agileboardsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Project createProject(ProjectDto newProject) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        Project project = new Project();
        project.setName(newProject.getName());
        project.setDescription(newProject.getDescription());
        project.setCreatedAt(LocalDateTime.now());
        project.setUser(user);

        return projectRepository.save(project);
    }

    public void deleteProject(String id) {
        // TODO: Check if username can delete this
        projectRepository.deleteById(UUID.fromString(id));
    }

    public List<Project> getAllProjects() {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        return projectRepository.findByUser(user);
    }

}
