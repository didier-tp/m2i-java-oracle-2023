
drop table if exists personne;

CREATE TABLE personne(
    id INTEGER auto_increment ,
	prenom VARCHAR(64),
	nom VARCHAR(64),
	PRIMARY KEY(id));                   
                   
INSERT INTO personne (prenom, nom) VALUES ('luc' , 'skywalker');
INSERT INTO personne (prenom, nom) VALUES ('jeanJean' , 'Bon');

SELECT * from personne;


	