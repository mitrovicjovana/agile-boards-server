package com.example.agileboardsserver.repository;

import com.example.agileboardsserver.model.Project;
import com.example.agileboardsserver.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepositoryTest;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown(){
        projectRepositoryTest.deleteAll();
    }

    @Test
    void itShouldFindProjectsBySearchTerm(){
        User user = new User(
                null,
                "Name",
                "Surname",
                "Username",
                "mail@mail.com",
                "password",
                true
        );

        userRepository.save(user);

        Project project = new Project(
                null,
                "Project name",
                "This is description.",
                LocalDateTime.now(),
                user
        );

        UUID projectId = projectRepositoryTest.save(project).getId();

        List<Project> projectList = projectRepositoryTest.findProjectsBySearchTerm("Project", user.getUsername());

        assertTrue(projectList.contains(project));
    }

    @Test
    void itShouldUpdateNameById(){
        User user = new User(
                null,
                "Name",
                "Surname",
                "Username",
                "mail@mail.com",
                "password",
                true
        );

        userRepository.save(user);

        Project project = new Project(
                null,
                "Project name",
                "This is description.",
                LocalDateTime.now(),
                user
        );

        UUID projectId = projectRepositoryTest.save(project).getId();
        String newProjectName = "New project name";

        project.setName(newProjectName);

        projectRepositoryTest.updateNameById(projectId, newProjectName);

        assertEquals(project, projectRepositoryTest.getById(projectId));
    }

    @Test
    void itShouldFindById() {
        User user = new User(
                null,
                "Name",
                "Surname",
                "Username",
                "mail@mail.com",
                "password",
                true
        );

        userRepository.save(user);

        Project project = new Project(
                null,
                "Project name",
                "This is description.",
                LocalDateTime.now(),
                user
        );

       UUID projectId = projectRepositoryTest.save(project).getId();

        assertEquals(project, projectRepositoryTest.findById(projectId).get());
    }

    @Test
    void itShouldNotFindById() throws Exception {
        User user = new User(
                null,
                "Name",
                "Surname",
                "Username",
                "mail@mail.com",
                "password",
                true
        );

        Project project = new Project(
                null,
                "Project name",
                "This is description.",
                LocalDateTime.now(),
                user
        );

        assertThrows(Exception.class, () -> projectRepositoryTest.findById(UUID.randomUUID()).get());
    }

}