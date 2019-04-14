# WindowCleaner
WC is a web app that let's authenticated users clean windows.

## Commands used
# Phase 1:

# Build
mvn clean compile package

# Docker related
# Build to be run from Project root
docker build -t <docker-image-name> .
e.g. docker build -t hello-okd-db .

# Tag the build: just gives a version to the docker image
docker tag <docker-image-name> agarhi/<docker-image-name>:<version>
e.g. docker tag hello-okd agarhi/hello-okd-db::0.0.1-SNAPSHOT

# Login to docker.io
docker login

# Upload to docker.io
docker push agarhi/<docker-image-name>:<version>
docker push agarhi/hello-okd-db::0.0.1-SNAPSHOT

## Kubernetes or minishift commands
# From: https://dzone.com/articles/deploying-a-spring-boot-app-with-mysql-on-openshif

minishift start
eval $(minishift oc-env)
eval $(minishift docker-env)
#https://stackoverflow.com/questions/52310599/what-does-minikube-docker-env-mean

oc login
docker login -u developer -p $(oc whoami -t) $(minishift openshift registry)

# MYSQL Image with your data very well explained here: https://medium.com/@lvthillo/customize-your-mysql-database-in-docker-723ffd59d8fb
1. Create SQL scripts
2. Put them inside sql-scripts folder inside the project root folder
3. In the root folder add a Docker file
4. Use docker build command, tag and push
   Note: The name of the database goes in as ENV MYSQL_DATABASE okddb in the Dockerfile
5. Later when you are in minishift you can use the following command to create a new app or pod from the docker image:
   oc new-app -e MYSQL_ROOT_USER=root -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=okddb --docker-image="agarhi/mysql-loaded:trail"
6. To login to the DB from minishift env
   oc rsh mysql-1-g4phb
   mysql -u root -p

# Minishift Spring boot app from docker image
oc new-app -e spring_datasource_url=jdbc:mysql://172.30.234.91:3306/okddb --docker-image="docker.io/agarhi/sb-ms-ms:trail3"
-e is to pass any environment variable to the spring boot app

oc expose svc/sb-ms-ms
To expose a route; The new route inherits the name from the service unless you specify one using the --name option

oc get pods
oc logs -f mysql-loaded-1-jnjnf
mysql-loaded-1-jnjnf is the pod name

oc get route
Will give you the route name, suppose route name is abcxyz - you just type http://abcxyz/rest/of/url in browswer to connect

#To delete resources 
oc delete deploymentconfigs sb-ms-ms
oc delete svc sb-ms-ms
oc delete pod <pod-name>


