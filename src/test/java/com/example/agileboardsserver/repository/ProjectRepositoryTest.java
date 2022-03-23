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
    private ProjectRepository underTest;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
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

        UUID projectId = underTest.save(project).getId();

        List<Project> projectList = underTest.findProjectsBySearchTerm("Project", user.getUsername());

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

        UUID projectId = underTest.save(project).getId();
        String newProjectName = "New project name";

        project.setName(newProjectName);

        underTest.updateNameById(projectId, newProjectName);

        assertEquals(project, underTest.getById(projectId));
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

       UUID projectId = underTest.save(project).getId();

        assertEquals(project, underTest.findById(projectId).get());
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

        assertThrows(Exception.class, () -> underTest.findById(UUID.randomUUID()).get());
    }

}