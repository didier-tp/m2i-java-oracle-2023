docker container stop mycontainer
docker container rm mycontainer
echo "http://localhost:8282/appliSpringWeb"
docker container run -p 8282:8181 \
 --network mynetwork \
 --name mycontainer -d   inetum/monappli:latest

