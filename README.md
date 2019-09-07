

## Freelance4j

**Assignment Lab:** Microservices using Red Hat OpenShift Application Runtimes.

![](/diagram.jpg)

**Table of Contents**

- [Freelance4j](#freelance4j)
  * [Github Repository url](#github-repository-url)
  * [Sample curl requests](#sample-curl-requests)
    + [API Gateway endpoints](#api-gateway-endpoints)
      - [GET all freelancers](#get-all-freelancers)
      - [GET freelancer by id](#get-freelancer-by-id)
      - [GET all projects](#get-all-projects)
      - [GET project by id](#get-project-by-id)
      - [GET project by status](#get-project-by-status)
    + [Freelancer Service Endpoints](#freelancer-service-endpoints)
      - [GET all freelancers](#get-all-freelancers-1)
      - [GET freelancer by id](#get-freelancer-by-id-1)
    + [Project Service Endpoints](#project-service-endpoints)
      - [GET all projects](#get-all-projects-1)
      - [GET project by id](#get-project-by-id-1)
      - [GET project by status](#get-project-by-status-1)
  * [Prerequisites](#prerequisites)
  * [Deploy to OpenShift](#deploy-to-openshift)
    + [Login and Create project](#login-and-create-project)
    + [Deploy Freelancer service (Spring boot)](#deploy-freelancer-service--spring-boot-)
      - [1. Deploy postgresql](#1-deploy-postgresql)
      - [2. Create configmap](#2-create-configmap)
      - [3. Build & Deploy Spring boot application](#3-build---deploy-spring-boot-application)
      - [4.Test Freelancer service endpoint](#4test-freelancer-service-endpoint)
    + [Deploy Project service (Vert.x)](#deploy-project-service--vertx-)
      - [1. Deploy MongoDB](#1-deploy-mongodb)
      - [2. Create configmap & set user policy](#2-create-configmap---set-user-policy)
      - [3. Build & Deploy Vert.x application](#3-build---deploy-vertx-application)
      - [4.Test Project service endpoint](#4test-project-service-endpoint)
    + [Deploy API Gateway service (Thorntail MicroProfile)](#deploy-api-gateway-service--thorntail-microprofile-)
      - [1. Create configmap](#1-create-configmap)
      - [2. Build & Deploy Vert.x application](#2-build---deploy-vertx-application)
      - [3.Test API Gateway service endpoint](#3test-api-gateway-service-endpoint)
  * [Clean up resources](#clean-up-resources)

### Github Repository url
#### https://github.com/sarawukl/RHOAR-Hands-on-assignment.git

### Sample curl requests
#### API Gateway endpoints
##### GET all freelancers
```
curl -X GET "http://gateway-service-sarawukl-lab10.apps-375d.generic.opentlc.com/gateway/freelancers"
```
##### GET freelancer by id
```
curl -X GET "http://gateway-service-sarawukl-lab10.apps-375d.generic.opentlc.com/gateway/freelancers/1"
```
##### GET all projects
```
curl -X GET "http://gateway-service-sarawukl-lab10.apps-375d.generic.opentlc.com/gateway/projects"
```
##### GET project by id
```
curl -X GET "http://gateway-service-sarawukl-lab10.apps-375d.generic.opentlc.com/gateway/projects/1"
```
##### GET project by status
```
curl -X GET "http://gateway-service-sarawukl-lab10.apps-375d.generic.opentlc.com/gateway/projects/status/in_progress"
```
#### Freelancer Service Endpoints
##### GET all freelancers
```
curl -X GET "http://freelancer-service-sarawukl-lab10.apps-375d.generic.opentlc.com/freelancers"
```
##### GET freelancer by id
```
curl -X GET "http://freelancer-service-sarawukl-lab10.apps-375d.generic.opentlc.com/freelancers/1"
```
#### Project Service Endpoints
##### GET all projects
```
curl -X GET "http://project-service-sarawukl-lab10.apps-375d.generic.opentlc.com/projects"
```
##### GET project by id
```
curl -X GET "http://project-service-sarawukl-lab10.apps-375d.generic.opentlc.com/projects/1"
```
##### GET project by status
```
curl -X GET "http://project-service-sarawukl-lab10.apps-375d.generic.opentlc.com/projects/status/in_progress"
```

### Prerequisites
- JDK 8.x
- Git 2.7.2 or later
- Maven 3.5.x
- OpenShift CLI (oc tools) v 3.11

### Deploy to OpenShift
#### Login and Create project
```
export OCP_SERVER_URL=<<your_ocp_server_url>>
export LAB10_PROJ=<<your_project_name>>
oc login --insecure-skip-tls-verify --server=${OCP_SERVER_URL}
oc new-project $LAB10_PROJ
```

#### Deploy Freelancer service (Spring boot)
##### 1. Deploy postgresql
```
oc process -f etc/freelancer-postgresql.yml -p FREELANCER_DB_USERNAME=freelancer_service -p FREELANCER_DB_PASSWORD=P@sssW0rdZZ -p FREELANCER_DB_NAME=freelancerdb | oc create -f - -n $LAB10_PROJ
```
##### 2. Create configmap
```
oc create configmap freelancer-service --from-file=etc/freelancer-application.yml -n $LAB10_PROJ
oc label configmap freelancer-service app=freelancer-service -n $LAB10_PROJ
```
##### 3. Build & Deploy Spring boot application
```
cd freelancer-service
mvn clean fabric8:deploy -Popenshift -Dfabric8.namespace=$LAB10_PROJ -DskipTests
cd ..
export FREELANCER_URL=http://$(oc get route freelancer-service -n $LAB10_PROJ -o template --template='{{.spec.host}}')
```
##### 4.Test Freelancer service endpoint
```
curl -X GET "$FREELANCER_URL/freelancers"
curl -X GET "$FREELANCER_URL/freelancers/1"
```

#### Deploy Project service (Vert.x)
##### 1. Deploy MongoDB
```
oc process -f etc/project-mongodb-persistent.yml -p PROJECT_DB_USERNAME=mongo -p PROJECT_DB_PASSWORD=mongo | oc create -f - -n $LAB10_PROJ
```
##### 2. Create configmap & set user policy
```
oc create configmap project-service --from-file=etc/project-config.yml -n $LAB10_PROJ
oc label configmap project-service app=project-service -n $LAB10_PROJ
oc policy add-role-to-user view -z default -n $LAB10_PROJ
```
##### 3. Build & Deploy Vert.x application
```
cd project-service
mvn clean fabric8:deploy -Popenshift -Dfabric8.namespace=$LAB10_PROJ -DskipTests
cd ..
export PROJECT_URL=http://$(oc get route project-service -n $LAB10_PROJ -o template --template='{{.spec.host}}')
```
##### 4.Test Project service endpoint
```
curl -X GET "$PROJECT_URL/projects"
curl -X GET "$PROJECT_URL/projects/1"
curl -X GET "$PROJECT_URL/projects/status/in_progress"
```

#### Deploy API Gateway service (Thorntail MicroProfile)
##### 1. Create configmap
```
echo $'freelancer-service:\n  url: '"$FREELANCER_URL"$'\nproject-service:\n  url: '"$PROJECT_URL"'' > etc/gateway-application.yml
oc create configmap gateway-service --from-file=etc/gateway-application.yml -n $LAB10_PROJ
oc label configmap gateway-service app=gateway-service -n $LAB10_PROJ
```
##### 2. Build & Deploy Vert.x application
```
cd gateway-service
mvn clean fabric8:deploy -Popenshift -Dfabric8.namespace=$LAB10_PROJ -DskipTests
cd ..
export GATEWAY_URL=http://$(oc get route gateway-service -n $LAB10_PROJ -o template --template='{{.spec.host}}')
```
##### 3.Test API Gateway service endpoint
```
curl -X GET "$GATEWAY_URL/gateway/freelancers"
curl -X GET "$GATEWAY_URL/gateway/freelancers/1"
curl -X GET "$GATEWAY_URL/gateway/projects"
curl -X GET "$GATEWAY_URL/gateway/projects/1"
curl -X GET "$GATEWAY_URL/gateway/projects/status/in_progress"
```

###  Clean up resources
```
./cleanup.sh
```
