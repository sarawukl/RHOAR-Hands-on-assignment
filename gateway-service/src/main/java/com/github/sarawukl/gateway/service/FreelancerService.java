package com.github.sarawukl.gateway.service;

import com.github.sarawukl.gateway.model.Freelancer;
import java.util.ArrayList;
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
public class FreelancerService {

  private WebTarget client;

  @Inject
  @ConfigurationValue("freelancer-service.url")
  private String freelancerUrl;

  @PostConstruct
  public void init() {
    client = ((ResteasyClientBuilder) ClientBuilder.newBuilder())
        .connectionPoolSize(10).build().target(freelancerUrl).path("freelancers");
  }

  public List<Freelancer> getFreelancers() {
    List<Freelancer> freelancers = new ArrayList<>();
    Response response = client.request(MediaType.APPLICATION_JSON).get(Response.class);
    if (response.getStatus() == 200) {
      freelancers = response.readEntity(new GenericType<List<Freelancer>>() {
      });
    } else {
      throw new ServiceUnavailableException();
    }
    return freelancers;
  }

  public Freelancer getFreelancer(String id) {
    Freelancer freelancer;
    Response response = client.path(id).request(MediaType.APPLICATION_JSON).get(Response.class);
    if (response.getStatus() == 200) {
      freelancer = response.readEntity(Freelancer.class);
    } else {
      throw new ServiceUnavailableException();
    }
    return freelancer;
  }
}
