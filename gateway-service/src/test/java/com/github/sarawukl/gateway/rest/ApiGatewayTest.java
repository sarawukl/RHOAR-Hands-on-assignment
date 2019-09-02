package com.github.sarawukl.gateway.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import com.github.sarawukl.gateway.RestApplication;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ApiGatewayTest {

  private static String port = "8081";

  private Client client;

  @Deployment
  public static Archive<?> createDeployment() {
    return ShrinkWrap.create(WebArchive.class)
        .addPackages(true, RestApplication.class.getPackage())
        .addAsResource("project-local.yml", "project-defaults.yml");
  }

  @Before
  public void before() throws Exception {
    client = ClientBuilder.newClient();
  }

  @After
  public void after() throws Exception {
    client.close();
  }

  @Test
  @RunAsClient
  public void testHealthCheck() throws Exception {
    WebTarget target = client.target("http://localhost:" + port).path("/health");
    Response response = target.request(MediaType.APPLICATION_JSON).get();
    assertThat(response.getStatus(), equalTo(new Integer(200)));
    JsonObject value = Json.parse(response.readEntity(String.class)).asObject();
    assertThat(value.getString("outcome", ""), equalTo("UP"));
  }
}
