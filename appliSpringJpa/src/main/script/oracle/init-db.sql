/*
 CE SCRIPT SERT A INITIALISER ENTIEREMENT UNE BASE DE DONNEES ORACLE
 (EN FIN DE Developpement, Debut de PRODUCTION)
 De manière à ce que l'appli SpringJPA puisse fonctionner normalement
 sans créations et réinitialisations automatiques des tables
 (sans option spring.jpa.hibernate.ddl-auto=create)
 ========
 CE SCRIPT EST UN DES LIVRABLES IMPORTANTS ATTENDUS !!!
 ========
 Avant de lancer ce script, il est conseillé de créer un USER... (ex: init-user.sql en SYSTEM)
 puis on lance ce script en étant connecté à sqlplus en tant que USER...
 après un début de session de ce genre:
  alter session set "_ORACLE_SCRIPT"=true;
 */


/*
 * Detruire les anciennes versions des séquences et tables
 * en faisant en sorte que les contraintes d'intégrité soient supprimées ou respéctées
 * en controllant bien l'ordre des suppressions (souvent ordre inverse des insert into)
 */
drop table adresse cascade constraints;
drop table adresse_de_personne cascade constraints;
drop table compte cascade constraints;
drop table compte_client cascade constraints;
drop table operation cascade constraints;
drop table personne cascade constraints;
drop sequence adresse_seq;
drop sequence compte_seq;
drop sequence operation_seq;
drop sequence personne_seq;

/*
 création des séquences oracles , 
 pour auto_incrémentation des clefs primaires
 */
create sequence adresse_seq start with 1 increment by 1;
create sequence compte_seq start with 1 increment by 1;
create sequence operation_seq start with 1 increment by 1;
create sequence personne_seq start with 1 increment by 1;

/*
 création des tables 
 */
create table adresse (id number(19,0) not null,
					code_postal varchar2(255), 
					rue varchar2(255), 
					ville varchar2(255), 
					primary key (id));
create table adresse_de_personne (id number(19,0) not null,
					code_postal varchar2(255),
					rue varchar2(255),
					ville varchar2(255),
					primary key (id));
create table compte (numero number(19,0) not null,
					label varchar2(255), 
					solde double precision, 
					primary key (numero));
create table compte_client (num_compte number(19,0) not null,
					num_client number(19,0) not null);
create table operation (id_op number(19,0) not null,
					date_op date, 
					label varchar2(255), 
					montant double precision, 
					num_compte number(19,0), 
					primary key (id_op));
create table personne (type_personne varchar2(31) not null,
					numero number(19,0) not null,
					etat varchar2(255),
					nom varchar2(255),
					prenom varchar2(64),
					telephone varchar2(255),
					email varchar2(255),
					id_adresse number(19,0),
					primary key (numero));
					
/*
 création des contraintes relationnelles (fk --> pk valide) 
 */				
alter table personne add constraint UK_6nau3abvodhi54ln7rjx4liao unique (id_adresse);
alter table adresse_de_personne add constraint FKbsng4prvgxw5cfa00s61e7jmo
foreign key (id) references personne;

alter table compte_client add constraint FKd7bi8a3nb8urkipsj0ug5lkdm 
foreign key (num_client) references personne;

alter table compte_client add constraint FKtdijggku081be8w75ypgg3wai
foreign key (num_compte) references compte;

alter table operation add constraint FKmesdd1k28dq0r18el7qmaayil 
foreign key (num_compte) references compte;

alter table personne add constraint FK16gp2enalx39031p3njj7l1xt 
foreign key (id_adresse) references adresse;

/*
 INSERT INTO .... VALUES ... (jeux de données)
 */

/*
 SELECT **** POUR VERIFIER
 */
SELECT * FROM adresse;
SELECT * FROM personne;
SELECT * FROM compte;
SELECT * FROM operation;
SELECT * FROM compte_client;
