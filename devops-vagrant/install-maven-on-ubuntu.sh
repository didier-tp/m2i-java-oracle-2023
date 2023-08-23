#!/bin/bash

wget https://dlcdn.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz

sudo tar xzf apache-maven-3.8.8-bin.tar.gz -C /usr/share

rm apache-maven-3.8.8-bin.tar.gz

sudo ln -s /usr/share/apache-maven-3.8.8 /usr/share/maven
sudo rm -f /etc/profile.d/maven.sh
sudo touch /etc/profile.d/maven.sh
echo "export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64" | sudo tee -a  /etc/profile.d/maven.sh
echo "export M2_HOME=/usr/share/maven" | sudo tee -a /etc/profile.d/maven.sh
echo "export MAVEN_HOME=/usr/share/maven" | sudo tee -a /etc/profile.d/maven.sh
echo 'export PATH=${M2_HOME}/bin:${PATH}' | sudo tee -a /etc/profile.d/maven.sh

source /etc/profile.d/maven.sh

mvn --version