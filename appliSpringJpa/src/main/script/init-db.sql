CREATE DATABASE IF NOT EXISTS BaseQuiVaBien ;
use BaseQuiVaBien;

DROP TABLE IF EXISTS employe;

CREATE TABLE employe(
   numero INTEGER auto_increment,
   prenom VARCHAR(64),
   nom VARCHAR(64),
   PRIMARY KEY(numero));

INSERT INTO employe (numero,prenom,nom)
VALUES (1,'alain', 'Therieur' );

INSERT INTO employe (numero,prenom,nom)
VALUES (2,'axelle', 'Aire' );

SELECT * FROM employe;