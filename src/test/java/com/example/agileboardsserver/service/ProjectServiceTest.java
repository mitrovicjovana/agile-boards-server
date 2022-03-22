package com.example.agileboardsserver.service;

import com.example.agileboardsserver.model.User;
import com.example.agileboardsserver.repository.ProjectRepository;
import com.example.agileboardsserver.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    private ProjectService projectServiceTest;
    private AutoCloseable closeable;


    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        projectServiceTest = new ProjectService(projectRepository, userRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        closeable.close();
    }

    @Test
    @Disabled
    void itShouldCreateProject() {
    }

    @Test
    @Disabled
    void isShouldDeleteProject() {

    }

    @Test
    @Disabled
    void getAllProjects() {

    }

    @Test
    @Disabled
    void getById() {
    }

    @Test
    @Disabled
    void update() {
    }
}