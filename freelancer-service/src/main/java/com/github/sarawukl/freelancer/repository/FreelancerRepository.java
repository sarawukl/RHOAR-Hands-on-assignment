package com.github.sarawukl.freelancer.repository;

import com.github.sarawukl.freelancer.model.Freelancer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {

  Optional<Freelancer> findAllByFreelancerId(Long id);
}
