curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key | sudo tee /usr/share/keyrings/jenkins-keyring.asc > /dev/null

echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
  
sudo apt-get update

sudo apt-get install jenkins -y

systemctl status jenkins
sudo usermod -a -G docker jenkins
systemctl restart jenkins
sudo ufw allow 8080
#sudo cat /var/lib/jenkins/secrets/initialAdminPassword
#plugins à installer : buildTimeout , git et pipeline
#nouveau username/password : admin/admin123
#url (en tp): http://localhost:8080
