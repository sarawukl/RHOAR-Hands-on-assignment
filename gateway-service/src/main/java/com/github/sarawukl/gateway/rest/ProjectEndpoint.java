package com.github.sarawukl.gateway.rest;

import com.github.sarawukl.gateway.model.Project;
import com.github.sarawukl.gateway.service.ProjectService;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/projects")
public class ProjectEndpoint {

  @Inject
  private ProjectService projectService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Project> getProjects() {
    return projectService.getProjects();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Project getProject(@PathParam("id") String id) {
    return projectService.getProject(id);
  }

  @GET
  @Path("status/{status}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Project> getProjectsByStatus(@PathParam("status") String status) {
    return projectService.getProjectsByStatus(status);
  }
}
