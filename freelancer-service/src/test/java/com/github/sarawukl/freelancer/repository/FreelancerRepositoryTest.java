package com.github.sarawukl.freelancer.repository;

import static org.junit.Assert.assertEquals;

import com.github.sarawukl.freelancer.model.Freelancer;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class FreelancerRepositoryTest {

  @Autowired
  private FreelancerRepository freelancerRepository;

  @Test
  public void testgetOneFreelancer() {
    Freelancer returnedFreelancers = freelancerRepository.getOne(1234L);
    assertEquals("Java, Thorntail, Vert.x, Spring Boot, JPA",
        returnedFreelancers.getSkills());
  }

  @Test
  public void testFindAllFreelancer() {
    List<Freelancer> returnedFreelancers = freelancerRepository.findAll();
    assertEquals(1, returnedFreelancers.size());
  }
}