package com.github.sarawukl.project.service;


import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class ProjectServiceTest extends MongoTestBase {

  private Vertx vertx;

  @Before
  public void setup(TestContext context) throws Exception {
    vertx = Vertx.vertx();
    vertx.exceptionHandler(context.exceptionHandler());
    JsonObject config = getConfig();
    mongoClient = MongoClient.createNonShared(vertx, config);
    Async async = context.async();
    dropCollection(mongoClient, "projects", async, context);
    async.await(10000);
  }

  @After
  public void tearDown() throws Exception {
    mongoClient.close();
    vertx.close();
  }

  @Test
  public void testGetProjects(TestContext context) throws Exception {
    Async saveAsync = context.async(1);
    String projectId1 = "111111";
    JsonObject json1 = new JsonObject()
      .put("projectId", projectId1)
      .put("ownerFirstName", "ownerFirstName1")
      .put("ownerLastName", "ownerLastName1")
      .put("ownerEmail", "ownerEmail1")
      .put("title", "title1")
      .put("description", "description1")
      .put("status", "status1");
    mongoClient.save("projects", json1, ar -> {
      if (ar.failed()) {
        context.fail();
      }
      saveAsync.countDown();
    });
    saveAsync.await();

    ProjectService service = new ProjectServiceImpl(vertx, getConfig(), mongoClient);

    Async async = context.async();

    service.getProjects(ar -> {
      if (ar.failed()) {
        context.fail(ar.cause().getMessage());
      } else {
        assertThat(ar.result(), notNullValue());
        assertThat(ar.result().size(), equalTo(1));
        Set<String> projects = ar.result().stream().map(p -> p.getProjectId())
          .collect(Collectors.toSet());
        assertThat(projects.size(), equalTo(1));
        assertThat(projects, allOf(hasItem(projectId1)));
        async.complete();
      }
    });
  }

  @Test
  public void testGetProjectsByStatus(TestContext context) throws Exception {
    Async saveAsync = context.async(2);
    String projectId1 = "111111";
    JsonObject json1 = new JsonObject()
      .put("projectId", projectId1)
      .put("ownerFirstName", "ownerFirstName1")
      .put("ownerLastName", "ownerLastName1")
      .put("ownerEmail", "ownerEmail1")
      .put("title", "title1")
      .put("description", "description1")
      .put("status", "status1");

    mongoClient.save("projects", json1, ar -> {
      if (ar.failed()) {
        context.fail();
      }
      saveAsync.countDown();
    });

    String projectId2 = "222222";
    JsonObject json2 = new JsonObject()
      .put("projectId", projectId2)
      .put("ownerFirstName", "ownerFirstName2")
      .put("ownerLastName", "ownerLastName2")
      .put("ownerEmail", "ownerEmail2")
      .put("title", "title2")
      .put("description", "description2")
      .put("status", "status2");

    mongoClient.save("projects", json2, ar -> {
      if (ar.failed()) {
        context.fail();
      }
      saveAsync.countDown();
    });
    saveAsync.await();

    ProjectService service = new ProjectServiceImpl(vertx, getConfig(), mongoClient);

    Async async = context.async();
    String status = "status1";

    service.getProjectsbyStatus(status, ar -> {
      if (ar.failed()) {
        context.fail(ar.cause().getMessage());
      } else {
        assertThat(ar.result(), notNullValue());
        assertThat(ar.result().size(), equalTo(1));
        Set<String> projects = ar.result().stream().map(p -> p.getProjectId())
          .collect(Collectors.toSet());
        assertThat(projects.size(), equalTo(1));
        assertThat(projects, allOf(hasItem(projectId1)));
        async.complete();
      }
    });
  }

  @Test
  public void testGetProject(TestContext context) throws Exception {
    Async saveAsync = context.async(1);
    String projectId1 = "111111";
    JsonObject json1 = new JsonObject()
      .put("projectId", projectId1)
      .put("ownerFirstName", "ownerFirstName1")
      .put("ownerLastName", "ownerLastName1")
      .put("ownerEmail", "ownerEmail1")
      .put("title", "title1")
      .put("description", "description1")
      .put("status", "status1");
    mongoClient.save("projects", json1, ar -> {
      if (ar.failed()) {
        context.fail();
      }
      saveAsync.countDown();
    });
    saveAsync.await();

    ProjectService service = new ProjectServiceImpl(vertx, getConfig(), mongoClient);

    Async async = context.async();

    service.getProject(projectId1, ar -> {
      if (ar.failed()) {
        context.fail(ar.cause().getMessage());
      } else {
        assertThat(ar.result(), notNullValue());
        assertThat(ar.result().getProjectId(), equalTo("111111"));
        assertThat(ar.result().getOwnerFirstName(), equalTo("ownerFirstName1"));
        async.complete();
      }
    });
  }

  @Test
  public void testGetNonExistingProject(TestContext context) throws Exception {
    Async saveAsync = context.async(1);
    String projectId1 = "111111";
    JsonObject json1 = new JsonObject()
      .put("projectId", projectId1)
      .put("ownerFirstName", "ownerFirstName1")
      .put("ownerLastName", "ownerLastName1")
      .put("ownerEmail", "ownerEmail1")
      .put("title", "title1")
      .put("description", "description1")
      .put("status", "status1");
    mongoClient.save("projects", json1, ar -> {
      if (ar.failed()) {
        context.fail();
      }
      saveAsync.countDown();
    });
    saveAsync.await();

    ProjectService service = new ProjectServiceImpl(vertx, getConfig(), mongoClient);

    Async async = context.async();

    service.getProject("1234", ar -> {
      if (ar.failed()) {
        context.fail(ar.cause().getMessage());
      } else {
        assertThat(ar.result(), nullValue());
        async.complete();
      }
    });
  }

  @Test
  public void testPing(TestContext context) throws Exception {
    ProjectService service = new ProjectServiceImpl(vertx, getConfig(), mongoClient);

    Async async = context.async();
    service.ping(ar -> {
      assertThat(ar.succeeded(), equalTo(true));
      async.complete();
    });
  }
}
