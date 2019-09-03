package com.github.sarawukl.gateway.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.sarawukl.gateway.model.Freelancer;
import com.github.sarawukl.gateway.model.Project;
import com.github.sarawukl.gateway.service.ProjectService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProjectEndpointTest {

  @Mock
  ProjectService projectService;

  @Spy
  @InjectMocks
  ProjectEndpoint projectEndpoint;

  @Test
  public void testGetProjects() {

    List<Project> projects = new ArrayList<>();
    Project returnedProject = new Project();
    returnedProject.setDescription("project1");
    projects.add(returnedProject);

    when(projectService.getProjects()).thenReturn(projects);

    List<Project> getProjects = projectEndpoint.getProjects();

    assertEquals(projects.size(), getProjects.size());
    verify(projectService, times(1)).getProjects();
  }

  @Test
  public void testGetProject() {

    Project returnedProject = new Project();
    returnedProject.setDescription("project1");
    returnedProject.setProjectId("1");

    when(projectService.getProject(anyString())).thenReturn(returnedProject);

    Project getProject = projectEndpoint.getProject("1");

    assertEquals(returnedProject.getProjectId(), getProject.getProjectId());
    verify(projectService, times(1)).getProject(anyString());
  }

  @Test
  public void testGetProjectsByStatus() {

    List<Project> projects = new ArrayList<>();
    Project returnedProject = new Project();
    returnedProject.setDescription("project1");
    returnedProject.setStatus("completed");
    projects.add(returnedProject);

    when(projectService.getProjectsByStatus(anyString())).thenReturn(projects);

    List<Project> getProjects = projectEndpoint.getProjectsByStatus(anyString());

    assertEquals(projects.size(), getProjects.size());
    verify(projectService, times(1)).getProjectsByStatus(anyString());
  }

}
