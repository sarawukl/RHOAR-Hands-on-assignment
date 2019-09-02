package com.github.sarawukl.gateway.rest;

import com.github.sarawukl.gateway.model.Freelancer;
import com.github.sarawukl.gateway.service.FreelancerService;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/freelancers")
@RequestScoped
public class FreelancerEndpoint {

  @Inject
  private FreelancerService freelancerService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Freelancer> getFreelancers() {
    return freelancerService.getFreelancers();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Freelancer getFreelancer(@PathParam("id") String id) {
    return freelancerService.getFreelancer(id);
  }

}
