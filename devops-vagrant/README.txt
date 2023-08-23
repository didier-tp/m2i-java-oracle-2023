vagrant plugin install vagrant-docker-compose
-----------------------------
a lancer dans l'ordre:
vagrant-up-cmd.bat (attendre)
vagrant-ssh.bat
....
   cd / vagrant
   bash install-maven-on-ubuntu.sh
   bash install-jenkins-on-ubuntu.sh

   ----------
   java -version
   mvn -version
   docker --version
   git --version
==========
http://localhost:8080 (console jenkins)
#recopier mot_passe_temporaire au premier demarrage
#plugins à installer : buildTimeout , git et pipeline
#nouveau username/password : admin/admin123
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
==> tous ces details dans le pipeline.txt

