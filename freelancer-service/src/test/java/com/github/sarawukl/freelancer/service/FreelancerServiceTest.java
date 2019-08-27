package com.github.sarawukl.freelancer.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.github.sarawukl.freelancer.model.Freelancer;
import com.github.sarawukl.freelancer.repository.FreelancerRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FreelancerServiceTest {

  @Mock
  private FreelancerRepository freelancerRepository;

  @Spy
  @InjectMocks
  private FreelancerService freelancerService;

  @Test
  public void testGetFreelancer() {
    Freelancer returnedFreelancer = new Freelancer();
    returnedFreelancer.setFirstName("TestGetOne");
    when(freelancerRepository.getOne(Long.valueOf("1234"))).thenReturn(returnedFreelancer);

    Freelancer getFreelancer = freelancerService.getFreelancer("1234");

    assertEquals(returnedFreelancer.getFirstName(), getFreelancer.getFirstName());
    verify(freelancerRepository, atLeastOnce()).getOne(anyLong());
    verify(freelancerService, times(1)).getFreelancer(anyString());
  }

  @Test
  public void testFindAllFreelancer() {
    List<Freelancer> returnedFreelancers = new ArrayList<>();
    returnedFreelancers.add(new Freelancer());
    when(freelancerRepository.findAll()).thenReturn(returnedFreelancers);

    List<Freelancer> getFreelancers = freelancerService.getFreelancers();

    assertEquals(returnedFreelancers.size(), getFreelancers.size());

    verify(freelancerRepository, atLeastOnce()).findAll();
    verify(freelancerService, times(1)).getFreelancers();
  }
}