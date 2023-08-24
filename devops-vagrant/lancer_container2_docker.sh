# commentaire (coherence config spring-boot et docker):
#spring.datasource.url=jdbc:mysql://mysqlhost:3306/Base3QuiVaBien?createDatabaseIfNotExist=true
#spring.datasource.username=root
#spring.datasource.password=root
docker container stop mycontainer_mysql
docker container rm mycontainer_mysql
docker container run \
 -p 3306:3306 \
 --network mynetwork \
 --name mycontainer_mysql \
 -e MYSQL_ROOT_PASSWORD=root \
 -h mysqlhost \
 -d   mysql:latest

