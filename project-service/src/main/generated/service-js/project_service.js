/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

/** @module service-js/project_service */
var utils = require('vertx-js/util/utils');
var Vertx = require('vertx-js/vertx');
var MongoClient = require('vertx-mongo-js/mongo_client');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JProjectService = Java.type('com.github.sarawukl.project.service.ProjectService');
var Project = Java.type('com.github.sarawukl.project.model.Project');

/**
 @class
*/
var ProjectService = function(j_val) {

  var j_projectService = j_val;
  var that = this;

  /**

   @public
   @param resulthandler {function} 
   */
  this.getProjects = function(resulthandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_projectService["getProjects(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        resulthandler(utils.convReturnListSetDataObject(ar.result()), null);
      } else {
        resulthandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param projectId {string} 
   @param resulthandler {function} 
   */
  this.getProject = function(projectId, resulthandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_projectService["getProject(java.lang.String,io.vertx.core.Handler)"](projectId, function(ar) {
      if (ar.succeeded()) {
        resulthandler(utils.convReturnDataObject(ar.result()), null);
      } else {
        resulthandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param status {string} 
   @param resulthandler {function} 
   */
  this.getProjectsbyStatus = function(status, resulthandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_projectService["getProjectsbyStatus(java.lang.String,io.vertx.core.Handler)"](status, function(ar) {
      if (ar.succeeded()) {
        resulthandler(utils.convReturnListSetDataObject(ar.result()), null);
      } else {
        resulthandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param resultHandler {function} 
   */
  this.ping = function(resultHandler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_projectService["ping(io.vertx.core.Handler)"](function(ar) {
      if (ar.succeeded()) {
        resultHandler(ar.result(), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_projectService;
};

ProjectService._jclass = utils.getJavaClass("com.github.sarawukl.project.service.ProjectService");
ProjectService._jtype = {
  accept: function(obj) {
    return ProjectService._jclass.isInstance(obj._jdel);
  },
  wrap: function(jdel) {
    var obj = Object.create(ProjectService.prototype, {});
    ProjectService.apply(obj, arguments);
    return obj;
  },
  unwrap: function(obj) {
    return obj._jdel;
  }
};
ProjectService._create = function(jdel) {
  var obj = Object.create(ProjectService.prototype, {});
  ProjectService.apply(obj, arguments);
  return obj;
}
/**

 @memberof module:service-js/project_service
 @param vertx {Vertx} 
 @param config {Object} 
 @param client {MongoClient} 
 @return {ProjectService}
 */
ProjectService.create = function(vertx, config, client) {
  var __args = arguments;
  if (__args.length === 3 && typeof __args[0] === 'object' && __args[0]._jdel && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'object' && __args[2]._jdel) {
    return utils.convReturnVertxGen(ProjectService, JProjectService["create(io.vertx.core.Vertx,io.vertx.core.json.JsonObject,io.vertx.ext.mongo.MongoClient)"](vertx._jdel, utils.convParamJsonObject(config), client._jdel));
  } else throw new TypeError('function invoked with invalid arguments');
};

/**

 @memberof module:service-js/project_service
 @param vertx {Vertx} 
 @return {ProjectService}
 */
ProjectService.createProxy = function(vertx) {
  var __args = arguments;
  if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._jdel) {
    return utils.convReturnVertxGen(ProjectService, JProjectService["createProxy(io.vertx.core.Vertx)"](vertx._jdel));
  } else throw new TypeError('function invoked with invalid arguments');
};

module.exports = ProjectService;