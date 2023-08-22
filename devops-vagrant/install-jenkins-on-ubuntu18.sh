sudo apt-get update
wget -q -O - https://pkg.jenkins.io/debian/jenkins.io.key | sudo apt-key add -
sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
sudo apt-get update
sudo apt install jenkins
systemctl status jenkins
sudo ufw allow 8080
#sudo cat /var/lib/jenkins/secrets/initialAdminPassword
#plugins à installer : buildTimeout et pipeline
#nouveau username/password : admin/admin123
#url (en tp): http://localhost:8080
