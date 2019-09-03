export LAB10_PROJ=sarawukl-lab10

# Deploy Freelancer Service
oc process -f etc/freelancer-postgresql.yml -p FREELANCER_DB_USERNAME=freelancer_service -p FREELANCER_DB_PASSWORD=P@sssW0rdZZ -p FREELANCER_DB_NAME=freelancerdb | oc create -f - -n $LAB10_PROJ
oc create configmap freelancer-service --from-file=etc/freelancer-application.yml -n $LAB10_PROJ
cd freelancer-service
mvn clean fabric8:deploy -Popenshift -Dfabric8.namespace=$LAB10_PROJ -DskipTests
cd ..
export FREELANCER_URL=http://$(oc get route freelancer-service -n $LAB10_PROJ -o template --template='{{.spec.host}}')

# Deploy Project Service
oc process -f etc/project-mongodb-persistent.yml -p PROJECT_DB_USERNAME=mongo -p PROJECT_DB_PASSWORD=mongo | oc create -f - -n $LAB10_PROJ
oc create configmap project-service --from-file=etc/project-config.yml -n $LAB10_PROJ
oc policy add-role-to-user view -z default -n $LAB10_PROJ
cd project-service
mvn clean fabric8:deploy -Popenshift -Dfabric8.namespace=$LAB10_PROJ -DskipTests
cd ..
export PROJECT_URL=http://$(oc get route project-service -n $LAB10_PROJ -o template --template='{{.spec.host}}')

# Deploy Gateway Service
oc create configmap gateway-service --from-file=etc/gateway-application.yml -n $LAB10_PROJ
cd gateway-service
mvn clean fabric8:deploy -Popenshift -Dfabric8.namespace=$LAB10_PROJ -DskipTests
cd ..
export GATEWAY_URL=http://$(oc get route gateway-service -n $LAB10_PROJ -o template --template='{{.spec.host}}')