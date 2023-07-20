create table compte (numero bigint not null auto_increment, label varchar(255), solde double precision, primary key (numero)) engine=InnoDB;
create table employe (numero bigint not null auto_increment, email varchar(255), nom varchar(255), prenom varchar(64), primary key (numero)) engine=InnoDB;
