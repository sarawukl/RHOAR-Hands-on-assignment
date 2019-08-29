package com.github.sarawukl.project.service;

import com.github.sarawukl.project.model.Project;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
//import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.healthchecks.Status;
import io.vertx.ext.mongo.MongoClient;
import java.util.List;

@VertxGen
@ProxyGen
public interface ProjectService {

  final static String ADDRESS = "project-service";

  static ProjectService create(Vertx vertx, JsonObject config, MongoClient client) {
    return new ProjectServiceImpl(vertx, config, client);
  }

  static ProjectService createProxy(Vertx vertx) {
    return new ProjectServiceVertxEBProxy(vertx, ADDRESS);
  }

  void getProjects(Handler<AsyncResult<List<Project>>> resulthandler);

  void getProject(String projectId, Handler<AsyncResult<Project>> resulthandler);

  void getProjectsbyStatus(String status, Handler<AsyncResult<List<Project>>> resulthandler);

  void ping(Handler<AsyncResult<String>> resultHandler);
}
