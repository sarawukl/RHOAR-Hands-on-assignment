export LAB10_PROJ=sarawukl-lab10
oc process -f etc/freelancer-postgresql.yml -p FREELANCER_DB_USERNAME=freelancer_service -p FREELANCER_DB_PASSWORD=P@sssW0rdZZ -p FREELANCER_DB_NAME=freelancerdb | oc create -f - -n $LAB10_PROJ
oc create configmap freelancer-service --from-file=etc/freelancer-application.yml -n $LAB10_PROJ
cd freelancer-service
mvn clean fabric8:deploy -Popenshift -Dfabric8.namespace=$LAB10_PROJ -DskipTests