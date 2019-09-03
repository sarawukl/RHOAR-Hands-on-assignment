package com.github.sarawukl.gateway.rest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.github.sarawukl.gateway.model.Freelancer;
import com.github.sarawukl.gateway.service.FreelancerService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FreelancerEndpointTest {

  @Mock
  FreelancerService freelancerService;

  @Spy
  @InjectMocks
  FreelancerEndpoint freelancerEndpoint;

  @Test
  public void testGetFreelancers() {

    List<Freelancer> freelancers = new ArrayList<>();
    Freelancer returnedFreelancer = new Freelancer();
    returnedFreelancer.setFirstName("freelancer1");
    freelancers.add(returnedFreelancer);

    when(freelancerService.getFreelancers()).thenReturn(freelancers);

    List<Freelancer> getFreelancers = freelancerEndpoint.getFreelancers();

    assertEquals(freelancers.size(), getFreelancers.size());
    verify(freelancerService, times(1)).getFreelancers();
  }

  @Test
  public void testGetFreelancer() {

    Freelancer returnedFreelancer = new Freelancer();
    returnedFreelancer.setFirstName("freelancer1");
    returnedFreelancer.setFreelancerId(1);

    when(freelancerService.getFreelancer(anyString())).thenReturn(returnedFreelancer);

    Freelancer getFreelancer = freelancerEndpoint.getFreelancer("1");

    assertEquals(returnedFreelancer.getFreelancerId(), getFreelancer.getFreelancerId());
    verify(freelancerService, times(1)).getFreelancer(anyString());
  }

}
