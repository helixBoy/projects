package com.incorta.projects.service;

import com.incorta.projects.dto.ProjectDto;
import com.incorta.projects.entity.*;
import com.incorta.projects.enums.ProjectState;
import com.incorta.projects.repository.ProjectRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @MockBean
    private ProjectRepository projectRepository;

    private static List<Project> projectList;
    private static Project project;

    @BeforeAll
    static void init() {
        // Setup mock repository
        project = Project.builder().name("project").state(ProjectState.ACTIVE)
                .progress(70.0).owner(Employee.builder().id(4).build()).build();
        projectList = new ArrayList<>();
        projectList.add(Project.builder().name("project1").state(ProjectState.PLANNED).build());
        projectList.add(Project.builder().name("project2").state(ProjectState.ACTIVE).progress(25.0).build());
        projectList.add(Project.builder().name("project3").state(ProjectState.DONE).build());
    }

    @Test
    @DisplayName("Test Find All projects")
    void testAllProjects() {
        doReturn(projectList).when(projectRepository).findAll();
        // Execute getAllProject service call
        List<Project> returnedProjectList = projectService.getAllProjects();
        // Assert the response
        Assertions.assertFalse(returnedProjectList.isEmpty(), "Project List was not found");
        Assertions.assertSame(returnedProjectList.size(), projectList.size(), "The Project List returned was not the same size as the mock");
    }

    @Test
    @DisplayName("Test Create new project")
    public void testCreateProject() {
        ProjectDto projectDto = ProjectDto.builder().name("project").state("active")
                .progress(70.0).ownerId(4L).build();
        doReturn(project).when(projectRepository).save(any());

        Project savedProject = projectService.saveProject(projectDto);
        Assertions.assertSame(savedProject.getId(), project.getId());
    }

}