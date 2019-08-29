package com.github.sarawukl.project.api;


import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.github.sarawukl.project.model.Project;
import com.github.sarawukl.project.service.ProjectService;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;

@RunWith(VertxUnitRunner.class)
public class ApiVerticleTest {

  private Vertx vertx;
  private Integer port;
  private ProjectService projectService;

  @Before
  public void setUp(TestContext context) throws IOException {
    vertx = Vertx.vertx();

    // Register the context exception handler
    vertx.exceptionHandler(context.exceptionHandler());

    // Let's configure the verticle to listen on the 'test' port (randomly picked).
    // We create deployment options and set the _configuration_ json object:
    ServerSocket socket = new ServerSocket(0);
    port = socket.getLocalPort();
    socket.close();

    DeploymentOptions options = new DeploymentOptions()
      .setConfig(new JsonObject().put("catalog.http.port", port));

    //Mock the catalog Service
    projectService = mock(ProjectService.class);

    // We pass the options as the second parameter of the deployVerticle method.
    vertx.deployVerticle(new ApiVerticle(projectService), options, context.asyncAssertSuccess());
  }

  @After
  public void tearDown(TestContext context) {
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void testGetProjects(TestContext context) throws Exception {

    String projectId1 = "111111";
    JsonObject json1 = new JsonObject()
      .put("projectId", projectId1)
      .put("ownerFirstName", "ownerFirstName1")
      .put("ownerLastName", "ownerLastName1")
      .put("ownerEmail", "ownerEmail")
      .put("title", "title1")
      .put("description", "description1")
      .put("status", "status1");
    List<Project> projects = new ArrayList<>();
    projects.add(new Project(json1));

    doAnswer((Answer<Void>) invocation -> {
      Handler<AsyncResult<List<Project>>> handler = invocation.getArgument(0);
      handler.handle(Future.succeededFuture(projects));
      return null;
    }).when(projectService).getProjects(any());

    Async async = context.async();
    vertx.createHttpClient().get(port, "localhost", "/projects", response -> {
      assertThat(response.statusCode(), equalTo(200));
      assertThat(response.headers().get("Content-type"), equalTo("application/json"));
      response.bodyHandler(body -> {
        JsonArray json = body.toJsonArray();
        Set<String> projectIds = json.stream()
          .map(j -> new Project((JsonObject) j))
          .map(p -> p.getProjectId())
          .collect(Collectors.toSet());
        assertThat(projectIds.size(), equalTo(1));
        assertThat(projectIds, allOf(hasItem(projectId1)));
        verify(projectService).getProjects(any());
        async.complete();
      })
        .exceptionHandler(context.exceptionHandler());
    })
      .exceptionHandler(context.exceptionHandler())
      .end();
  }

  @Test
  public void testGetProjectByStatus(TestContext context) throws Exception {

    String projectId1 = "111111";
    JsonObject json1 = new JsonObject()
      .put("projectId", projectId1)
      .put("ownerFirstName", "ownerFirstName1")
      .put("ownerLastName", "ownerLastName1")
      .put("ownerEmail", "ownerEmail")
      .put("title", "title1")
      .put("description", "description1")
      .put("status", "status1");
    List<Project> projects = new ArrayList<>();
    projects.add(new Project(json1));

    doAnswer((Answer<Void>) invocation -> {
      Handler<AsyncResult<List<Project>>> handler = invocation.getArgument(1);
      handler.handle(Future.succeededFuture(projects));
      return null;
    }).when(projectService).getProjectsbyStatus(eq("status1"), any());

    Async async = context.async();
    vertx.createHttpClient().get(port, "localhost", "/projects/status/status1", response -> {
      assertThat(response.statusCode(), equalTo(200));
      assertThat(response.headers().get("Content-type"), equalTo("application/json"));
      response.bodyHandler(body -> {
        JsonArray json = body.toJsonArray();
        Set<String> projectIds = json.stream()
          .map(j -> new Project((JsonObject) j))
          .map(p -> p.getProjectId())
          .collect(Collectors.toSet());
        assertThat(projectIds.size(), equalTo(1));
        assertThat(projectIds, allOf(hasItem(projectId1)));
        verify(projectService).getProjectsbyStatus(eq("status1"), any());
        async.complete();
      })
        .exceptionHandler(context.exceptionHandler());
    })
      .exceptionHandler(context.exceptionHandler())
      .end();
  }

  @Test
  public void testGetProject(TestContext context) throws Exception {

    String projectId1 = "111111";
    JsonObject json1 = new JsonObject()
      .put("projectId", projectId1)
      .put("ownerFirstName", "ownerFirstName1")
      .put("ownerLastName", "ownerLastName1")
      .put("ownerEmail", "ownerEmail")
      .put("title", "title1")
      .put("description", "description1")
      .put("status", "status1");
    Project project = new Project(json1);

    doAnswer((Answer<Void>) invocation -> {
      Handler<AsyncResult<Project>> handler = invocation.getArgument(1);
      handler.handle(Future.succeededFuture(project));
      return null;
    }).when(projectService).getProject(eq("111111"), any());

    Async async = context.async();
    vertx.createHttpClient().get(port, "localhost", "/projects/111111", response -> {
      assertThat(response.statusCode(), equalTo(200));
      assertThat(response.headers().get("Content-type"), equalTo("application/json"));
      response.bodyHandler(body -> {
        JsonObject result = body.toJsonObject();
        assertThat(result, notNullValue());
        assertThat(result.containsKey("projectId"), is(true));
        assertThat(result.getString("projectId"), equalTo("111111"));
        verify(projectService).getProject(eq("111111"), any());
        async.complete();
      })
        .exceptionHandler(context.exceptionHandler());
    })
      .exceptionHandler(context.exceptionHandler())
      .end();
  }


  @Test
  public void testGetNonExistingProject(TestContext context) throws Exception {

    doAnswer((Answer<Void>) invocation -> {
      Handler<AsyncResult<Project>> handler = invocation.getArgument(1);
      handler.handle(Future.succeededFuture(null));
      return null;
    }).when(projectService).getProject(eq("111111"), any());

    Async async = context.async();
    vertx.createHttpClient().get(port, "localhost", "/projects/111111", response -> {
      assertThat(response.statusCode(), equalTo(404));
      async.complete();
    })
      .exceptionHandler(context.exceptionHandler())
      .end();
  }

}
