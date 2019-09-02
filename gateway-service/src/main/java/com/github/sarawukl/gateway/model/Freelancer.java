package com.github.sarawukl.gateway.model;

import lombok.Data;

@Data
public class Freelancer {

  private long freelancerId;
  private String firstName;
  private String lastName;
  private String email;
  private String skills;

}
