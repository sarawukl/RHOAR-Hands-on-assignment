package com.github.sarawukl.freelancer.service;

import com.github.sarawukl.freelancer.model.Freelancer;
import com.github.sarawukl.freelancer.repository.FreelancerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreelancerService {

  @Autowired
  private FreelancerRepository freelancerRepository;


  public Freelancer getFreelancer(String id) {
    return freelancerRepository.findAllByFreelancerId(Long.valueOf(id)).orElse(null);
  }

  public List<Freelancer> getFreelancers() {
    return freelancerRepository.findAll();
  }
}
