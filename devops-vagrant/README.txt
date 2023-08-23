vagrant plugin install vagrant-docker-compose
-----------------------------
java -version
mvn -version
docker --version
git --version
==========
NB: l'application utilisée pour les tests "appliSpringWeb3"
a comme url http://localhost:8181/appliSpringWeb 
pour éviter un conflit avec jenkins qui tourne par défaut sur localhost:8080
et tous les tests unitaires sont en @ActiveProfiles({"h2"}) 
-----
job "appliSpringWeb3Pipeline"
avec url git = https://github.com/didier-tp/m2i-java-oracle-2023
et avec branche main plutot que master
cible maven de au niveau : clean package
et chemin menant au pom.xml = appliSpringWeb3/pom.xml
==> tous ces details dans le pipeline

