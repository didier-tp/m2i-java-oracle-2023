CREATE DATABASE IF NOT EXISTS BaseQuiVaBien ;
use BaseQuiVaBien;



DROP TABLE IF EXISTS compte_client;
DROP TABLE IF EXISTS operation;
DROP TABLE IF EXISTS compte;
DROP TABLE IF EXISTS client;

DROP TABLE IF EXISTS employe;

CREATE TABLE employe(
   numero INTEGER auto_increment,
   prenom VARCHAR(64),
   nom VARCHAR(64),
   PRIMARY KEY(numero));
   
create table client (
    numero bigint not null auto_increment,
    nom varchar(255), 
    prenom varchar(64), 
    primary key (numero));
    
create table compte (
    numero bigint not null auto_increment,
    label varchar(255), 
    solde double precision, 
    primary key (numero)
    ) ;  
    
create table operation (
   id_op bigint not null auto_increment,
   date date, 
   label varchar(255), 
   montant double precision, 
   num_compte bigint, 
   primary key (id_op)
   );  
   
alter table operation add constraint operation_avec_compte_valide
foreign key (num_compte) references compte (numero);  
   
create table compte_client (
     num_compte bigint not null, 
     num_client bigint not null,
     primary key (num_compte,num_client)
     );
     
alter table compte_client add constraint associer_client_existant_a_un_compte
foreign key (num_client) references client (numero);

alter table compte_client add constraint associer_compte_existant_a_un_client
foreign key (num_compte) references compte (numero);
   

INSERT INTO employe (numero,prenom,nom) VALUES (1,'alain', 'Therieur' );
INSERT INTO employe (numero,prenom,nom) VALUES (2,'axelle', 'Aire' );

INSERT INTO client (numero,prenom,nom) VALUES (1,'ali', 'gator' );
INSERT INTO client (numero,prenom,nom) VALUES (2,'olie', 'condor' );

INSERT INTO compte (numero,label,solde) VALUES (1,'compteXxxx', 69.9 );
INSERT INTO compte (numero,label,solde) VALUES (2,'compteYyyy', 567.9 );
INSERT INTO compte (numero,label,solde) VALUES (3,'compteZzzz', 167.9 );

INSERT INTO operation (id_op,label,montant,date,num_compte) 
    VALUES (1,'achat_aa', -45.6 , "2023-04-22" , 1 );
INSERT INTO operation (id_op,label,montant,date,num_compte) 
    VALUES (2,'achat_bb', -35.6 , "2023-04-23" , 1 ); 
    
INSERT INTO operation (id_op,label,montant,date,num_compte) 
    VALUES (3,'achat_cc', -25.6 , "2023-04-22" , 2 );
INSERT INTO operation (id_op,label,montant,date,num_compte) 
    VALUES (4,'achat_dd', -15.6 , "2023-04-23" , 2 );     

INSERT INTO compte_client (num_compte,num_client) VALUES (1,1);
INSERT INTO compte_client (num_compte,num_client) VALUES (2,1); 
INSERT INTO compte_client (num_compte,num_client) VALUES (3,2); 

SELECT * FROM employe;
SELECT * FROM client;
SELECT * FROM compte;
SELECT * FROM operation;
SELECT * FROM compte_client;