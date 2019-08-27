package com.github.sarawukl.freelancer.controller;

import com.github.sarawukl.freelancer.model.Freelancer;
import com.github.sarawukl.freelancer.service.FreelancerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/freelancers")
public class FreelancerController {

  @Autowired
  private FreelancerService freelancerService;

  @GetMapping()
  public List<Freelancer> getFreelancers() {
    return freelancerService.getFreelancers();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Freelancer getFreelancer(@PathVariable String id) {
    return freelancerService.getFreelancer(id);
  }
}
