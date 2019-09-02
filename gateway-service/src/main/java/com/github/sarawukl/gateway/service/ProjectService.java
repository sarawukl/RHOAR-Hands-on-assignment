package com.github.sarawukl.gateway.service;

import com.github.sarawukl.gateway.model.Project;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

@ApplicationScoped
public class ProjectService {

  private WebTarget client;

  @Inject
  @ConfigurationValue("project-service.url")
  private String projectUrl;

  @PostConstruct
  public void init() {
    client = ((ResteasyClientBuilder) ClientBuilder.newBuilder())
        .connectionPoolSize(10).build().target(projectUrl).path("projects");
  }

  public List<Project> getProjects() {
    List<Project> projects;
    Response response = client.request(MediaType.APPLICATION_JSON).get(Response.class);
    if (response.getStatus() == 200) {
      projects = response.readEntity(new GenericType<List<Project>>() {
      });
    } else {
      throw new ServiceUnavailableException();
    }
    return projects;
  }

  public Project getProject(String id) {
    Project project;
    Response response = client.path(id).request(MediaType.APPLICATION_JSON).get(Response.class);
    if (response.getStatus() == 200) {
      project = response.readEntity(Project.class);
    } else {
      throw new ServiceUnavailableException();
    }
    return project;
  }

  public List<Project> getProjectsByStatus(String status) {
    List<Project> projects;
    Response response = client.path("status").path(status).request(MediaType.APPLICATION_JSON)
        .get(Response.class);
    if (response.getStatus() == 200) {
      projects = response.readEntity(new GenericType<List<Project>>() {
      });
    } else {
      throw new ServiceUnavailableException();
    }
    return projects;
  }
}
