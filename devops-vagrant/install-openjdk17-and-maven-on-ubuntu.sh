#sudo apt-get update
sudo apt-get -y install openjdk-17-jdk
java -version
#sudo apt-get -y install maven
#Attention maven par defaut pour ubuntu 18.04 = 3.6 ne supportant pas java 17
wget https://dlcdn.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz
sudo tar xzf apache-maven-3.8.8-bin.tar.gz -C /usr/share
sudo ln -s /usr/share/apache-maven-3.8.8 /usr/share/maven
echo "export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64" | sudo tee -a  /etc/profile.d/maven.sh
echo "export M2_HOME=/usr/share/maven" | sudo tee -a /etc/profile.d/maven.sh
echo "export MAVEN_HOME=/usr/share/maven" | sudo tee -a /etc/profile.d/maven.sh
echo 'export PATH=${M2_HOME}/bin:${PATH}' | sudo tee -a /etc/profile.d/maven.sh
source /etc/profile.d/maven.sh
mvn --version