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
   NOTE: mysql-1-g4phb is the pod name for mysql

   NOTE: A DB cannot be exposed to outside world like a web app. You have to use port forwarding. More here: https://blog.openshift.com/openshift-connecting-database-using-port-forwarding/
   Command: 
	oc port-forward <pod-name> <local-port>:<remote:port>
	oc port-forward database-1-9xv8n 13306:3306
   Where port-forward database-1-9xv8n is the pod name, 133036 is the local port and 3306 is the port in OpenShift
  

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

Sometimes pods will recreate after deletion, for that oc get deploymentconfigs and delete each deploymentconfig and pods will delete

# Some helpful curl commands to invoke the application
## This command prints both JSESSIONID cookie and auth token. Save them in s and t respectively
curl -c - -X POST --user "okd-client:okd-secret" -d "grant_type=password&username=Asif&password=asif" "http://127.0.0.1:8082/HelloWorldExample/oauth/token" 
## With the auth token and sesison id send request to an API
curl -i -H "Accept: application/json" -H "Authorization: Bearer $t" --cookie "JSESSIONID=$s" -X GET "http://127.0.0.1:8082/HelloWorldExample/okd/v1/clean"

# Redis local set up
https://redis.io/topics/quickstart
## I unzipped inside /Users/asif.garhi/redis-stable
## the last command didn't work so I looked at https://stackoverflow.com/questions/47195029/how-to-run-the-equivalent-of-sudo-update-rc-d-redis-6379-defaults-in-centos and then this https://community.pivotal.io/s/article/How-to-install-and-use-Redis-on-Linux but I didn't follow this link becaue it seems I have done most of it already

## REDIS minishift set up YAYY !!
# Inside minishift start
docker pull redis
# Above gets redis image from https://hub.docker.com/_/redis and puts it in local minishift space

# This will deploy it on Minishift and also start the server
oc new-app --docker-image=redis

# Not totally sure if this is needed but I did it
oc expose svc/redis

# Login to the instance from minishift
oc rsh redis-1-pzph8 
(redis-1-pzph8 is the pod name for redis)
And just start using redis-cli e.g. :

$ redis-cli set key value
OK
$ redis-cli get key
"value"
$ 

# To know the IP:port of redis within MS (Minishift):

MLJ020G8WL:Downloads asif.garhi$ oc get svc
NAME                           TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
redis                          ClusterIP   172.30.44.110   <none>        6379/TCP   1h
window-cleaner-testredisconn   ClusterIP   172.30.33.62    <none>        8090/TCP   27s

# Not sure if getting svc requires oc expose svc/redis; I think not but not sure.

# Now within a Spring Boot image that is running on same MS you can simply connect using this host and port
# Look at https://github.com/agarhi/WindowCleanerTestRedisConnectivity; command used: 
oc new-app -e spring_redis_host=172.30.44.110 -e spring_redis_port=6379 --docker-image="docker.io/agarhi/window-cleaner-testredisconn:1.0.0.1"
 

######################################################################################################################################################
Example of how you pass env var to SB:
- java -Dspring_datasource_url="jdbc:mysql://localhost:3306/okddb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=PST" -jar target/WindowCleaner-0.0.1-SNAPSHOT.jar
- java -Dspring_redis_host=localhost -Dspring_redis_port=6379 -jar target/WindowCleanerTestRedisConn-0.0.1-SNAPSHOT.jar
######################################################################################################################################################
