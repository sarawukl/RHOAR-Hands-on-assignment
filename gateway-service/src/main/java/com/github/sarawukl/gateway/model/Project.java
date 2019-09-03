package com.github.sarawukl.gateway.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class Project implements Serializable {

  private String projectId;
  private String ownerFirstName;
  private String ownerLastName;
  private String ownerEmail;
  private String title;
  private String description;
  private String status;

}
