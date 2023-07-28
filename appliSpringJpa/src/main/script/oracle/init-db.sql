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
alter session set "_ORACLE_SCRIPT"=true;

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
					code_postal varchar2(16), 
					rue varchar2(32), 
					ville varchar2(32), 
					primary key (id));
create table adresse_de_personne (id number(19,0) not null,
					code_postal varchar2(16),
					rue varchar2(32),
					ville varchar2(32),
					primary key (id));
create table compte (numero number(19,0) ,
					label varchar2(32), 
					solde double precision, 
					primary key (numero));
/*					
CREATE OR REPLACE TRIGGER compte_insert_trg
BEFORE INSERT ON compte
FOR EACH ROW
WHEN (new.numero is null)
BEGIN
  SELECT compte_seq.NEXTVAL
  INTO   :new.numero
  FROM   dual;
END;
/					
*/	
						
create table compte_client (num_compte number(19,0) not null,
					num_client number(19,0) not null);
create table operation (id_op number(19,0) not null,
					date_op date, 
					label varchar2(64), 
					montant double precision, 
					num_compte number(19,0), 
					primary key (id_op));
create table personne (type_personne varchar2(31) not null,
					numero number(19,0) not null,
					etat varchar2(16),
					nom varchar2(32),
					prenom varchar2(32),
					telephone varchar2(16),
					email varchar2(64),
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

INSERT INTO adresse(id,code_postal,rue,ville) 
VALUES (1,'76000' ,'rue beauvoisine' , 'Rouen');

INSERT INTO adresse(id,code_postal,rue,ville) 
VALUES (2,'80000' ,'rue qui va bien' , 'Amiens');

INSERT INTO personne(type_personne,numero,etat,prenom,nom,telephone,email,id_adresse) 
VALUES ('Client' , 1 , 'ENDORMIE' , 'olie' ,'Condor','0102030405', null , 1 );

INSERT INTO personne(type_personne,numero,etat,prenom,nom,telephone,email,id_adresse) 
VALUES ('Client' , 2 , 'REVEILLEE' , 'luc' ,'SkyWalker','0201030405', null , 2 );

INSERT INTO personne(type_personne,numero,etat,prenom,nom,telephone,email,id_adresse) 
VALUES ('Employe' , 3 , 'REVEILLEE' , 'alex' ,'Therieur',null, 'ls@xy.com' , null);

INSERT INTO personne(type_personne,numero,etat,prenom,nom,telephone,email,id_adresse) 
VALUES ('Personne' , 4 , 'REVEILLEE' , 'alaix' ,'Therieur',null, null , null );


INSERT INTO compte(numero,label,solde) 
VALUES (1,'Compte_c1' , 50.0);
INSERT INTO compte(numero,label,solde) 
VALUES (2,'Compte_c2' , 60.0);
INSERT INTO compte(numero,label,solde) 
VALUES (3,'Compte_c3' , 70.0);
INSERT INTO compte(numero,label,solde) 
VALUES (4,'Compte_c4' , 80.0);
INSERT INTO compte(label,solde) VALUES ('Compte_c5' , 100.0);

INSERT INTO operation(id_op,date_op,label,montant,num_compte) 
VALUES (1,to_date('2023-07-27','yyyy-MM-dd') , 'achat1a' , -3.3 , 1);
INSERT INTO operation(id_op,date_op,label,montant,num_compte) 
VALUES (2,to_date('2023-07-28','yyyy-MM-dd') , 'achat1b' , -4.4 , 1);
INSERT INTO operation(id_op,date_op,label,montant,num_compte) 
VALUES (3,to_date('2023-07-28','yyyy-MM-dd') , 'achat2a' , -5.5 ,2)

INSERT INTO compte_client(num_compte,num_client) VALUES (1,1);
INSERT INTO compte_client(num_compte,num_client) VALUES (2,1);
INSERT INTO compte_client(num_compte,num_client) VALUES (3,2);
INSERT INTO compte_client(num_compte,num_client) VALUES (4,2);


/*
 SELECT **** POUR VERIFIER
 */
SELECT * FROM adresse;
SELECT * FROM personne;
SELECT * FROM compte;
SELECT * FROM operation;
SELECT * FROM compte_client;
