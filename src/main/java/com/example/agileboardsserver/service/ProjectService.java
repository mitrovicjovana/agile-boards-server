package com.example.agileboardsserver.service;

import com.example.agileboardsserver.dto.CreateProject;
import com.example.agileboardsserver.dto.ProjectDto;
import com.example.agileboardsserver.model.Project;
import com.example.agileboardsserver.model.User;
import com.example.agileboardsserver.repository.ProjectRepository;
import com.example.agileboardsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<ProjectDto> findProjectsBySearchTerm(String searchTerm){
        List<ProjectDto> projects = new ArrayList<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        projectRepository.findProjectsBySearchTerm(searchTerm, username)
                .forEach(project -> {
                    projects.add(getProjectDto(project));
                });

        return projects;
    }

    public ProjectDto createProject(CreateProject newProject) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        Project project = new Project();
        project.setName(newProject.getName());
        project.setDescription(newProject.getDescription());
        project.setCreatedAt(LocalDateTime.now());
        project.setUser(user);

       return getProjectDto(projectRepository.save(project));
    }

    @Transactional
    public void deleteProject(String id) {
        Project project = projectRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Project not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(project.getUser().getUsername().equals(username)) projectRepository.deleteById(UUID.fromString(id));
        else throw new RuntimeException("Unauthorized action");
    }

    public List<ProjectDto> getAllProjects() {
        List<ProjectDto> projects = new ArrayList<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Problem occurred while fetching projects from database"));

        projectRepository.findByUser(user).forEach(project -> {
            projects.add(getProjectDto(project));
        });
        return projects;
    }

    public ProjectDto getById(String id) {
        Project project = projectRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Project not found"));

         return getProjectDto(project);
    }

    @Transactional
    public ProjectDto update(ProjectDto project) {
        Project _project = projectRepository
                .findById(project.getId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        _project.setName(project.getName());
        _project.setDescription(project.getDescription());

        Project updatedProject = projectRepository.save(_project);
        return getProjectDto(updatedProject);
    }

    private ProjectDto getProjectDto(Project _project){
        ProjectDto.ProjectUserDto userDto = new ProjectDto.ProjectUserDto();
        userDto.setId(_project.getUser().getId());
        userDto.setFirstName(_project.getUser().getFirstName());
        userDto.setLastName(_project.getUser().getLastName());
        userDto.setUsername(_project.getUser().getUsername());

        ProjectDto project = new ProjectDto();
        project.setId(_project.getId());
        project.setName(_project.getName());
        project.setDescription(_project.getDescription());
        project.setCreatedAt(_project.getCreatedAt());
        project.setUser(userDto);

        return project;
    }

}
