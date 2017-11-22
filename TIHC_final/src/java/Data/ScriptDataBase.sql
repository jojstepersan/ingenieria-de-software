-- username: root
-- pass: 12345
drop database TIHC;

create database TIHC;

use TIHC;

-- tabla cliente
create table cliente(
cod_cliente int  primary key,
nom_cliente varchar(60),
ape_cliente varchar(60)
); 

-- tabla pais
create table pais(
cod_pais int primary key,
nom_pais varchar(100),
ubicacion_x float,
ubicacion_y float
);

-- tabla puerto
create table puerto(
cod_puerto int primary key,
nom_puerto varchar(70),
cod_pais int,
constraint pais_fk foreign key(cod_pais) references pais(cod_pais)
);

-- tabla contrato
create table contrato(
cod_contrato int primary key, -- codigo radicacion
cod_cliente int,
constraint cliente_fk foreign key(cod_cliente) references cliente(cod_cliente)
);

-- tabla tipo carga
create table tipo_carga(
cod_tipo_carga int primary key,
nom_tipo_carga varchar(100)
);

-- tabla estado
create table estado(
cod_estado int primary key,
nom_estado varchar(60),
descripcion varchar(400)
);

-- tipo empleado
create table tipo_empleado(
cod_tipo_empleado int primary key,
nom_tipo_empleado varchar(60)
);

-- tabla barco
create table barco(
cod_barco int primary key,
nom_barco varchar(100),
peso int,
cod_estado int,
fecha_adquisicion date,
fecha_ultimo_mantenimiento date,
constraint estado_fk foreign key(cod_estado) references estado(cod_estado)
);

-- tabla empleado
create table tripulante(
cod_empleado int primary key,
cod_barco int,
nom_empleado varchar(60),
ape_empleado varchar(60),
tipo_empleado int,
constraint barco_fk foreign key(cod_barco) references barco(cod_barco),
constraint tipo_empleado_fk foreign key (tipo_empleado) references tipo_empleado(cod_tipo_empleado)   
);


-- tabla carga
create table carga(
cod_carga int primary key,
cod_contrato int,
cod_barco int,
cod_tipo_carga int,
peso int,
origen int,
destino int,
descripcion varchar(400),
constraint tipo_carga_fk foreign key(cod_tipo_carga) references tipo_carga(cod_tipo_carga),
constraint barco2_fk foreign key(cod_barco) references barco(cod_barco),
constraint origen_fk foreign key(origen) references pais(cod_pais),
constraint destino_fk foreign key(destino) references pais(cod_pais),
constraint contrato_fk foreign key(cod_contrato) references contrato(cod_contrato)
);

-- tabla usuario para el login
create table usuario(
cod_usuario int primary key,
cod_tipo_empleado int,
nom_usuario varchar(100),
ape_usuario varchar(100),
username varchar(100),
pass varchar(200),
constraint tipo_empleado2_fk foreign key (cod_tipo_empleado) references tipo_empleado(cod_tipo_empleado)   
);


insert into tipo_empleado values(1,'capitan');
insert into tipo_empleado values(2,'tripulante');
insert into tipo_empleado values(3,'usuario');
insert into tipo_empleado values(4,'admin');

insert into estado values(1,'Listo','Listo para salir');
insert into barco values(1,'el perla negra',100,1,'2017-09-08','2017-09-08');

-- tabla itinerario
-- create table itinerario(
-- cod_itinerario int primary key,
-- descripcion varchar(400),
-- hora_salida datetime,
-- hora_llegada datetime
-- );

insert into pais(cod_pais,nom_pais) values(1,'Shanghai');
insert into pais(cod_pais,nom_pais) values(2,'Singapore');
insert into pais(cod_pais,nom_pais) values(3,'Busan');
insert into pais(cod_pais,nom_pais) values(4,'Hong kong');
insert into pais(cod_pais,nom_pais) values(5,'Jebel Ali');
insert into pais(cod_pais,nom_pais) values(6,'Rotterdam');
insert into pais(cod_pais,nom_pais) values(7,'Klang');
insert into pais(cod_pais,nom_pais) values(8,'Antwerp');
insert into pais(cod_pais,nom_pais) values(9,'Hamburg');
insert into pais(cod_pais,nom_pais) values(10,'Los Angeles');
insert into pais(cod_pais,nom_pais) values(11,'Akita');
insert into pais(cod_pais,nom_pais) values(12,'Long Beach');
insert into pais(cod_pais,nom_pais) values(13,'Ho Chi Minh');
insert into pais(cod_pais,nom_pais) values(14,'Jakarta');
insert into pais(cod_pais,nom_pais) values(15,'Columbo');
insert into pais(cod_pais,nom_pais) values(16,'Valencia');
insert into pais(cod_pais,nom_pais) values(17,'Manila');
insert into pais(cod_pais,nom_pais) values(18,'Jeddah');
insert into pais(cod_pais,nom_pais) values(19,'Jawaharlal Nehru');
insert into pais(cod_pais,nom_pais) values(20,'Felixstone');
insert into pais(cod_pais,nom_pais) values(21,'Santos');
insert into pais(cod_pais,nom_pais) values(22,'Port Said');
insert into pais(cod_pais,nom_pais) values(23,'Colon');
insert into pais(cod_pais,nom_pais) values(24,'Piraeus');
insert into pais(cod_pais,nom_pais) values(25,'Ambarli');
insert into pais(cod_pais,nom_pais) values(26,'Marsaxlokk');
insert into pais(cod_pais,nom_pais) values(27,'Vancouver');
insert into pais(cod_pais,nom_pais) values(28,'Barrancabermeja');
insert into pais(cod_pais,nom_pais) values(29,'Veracruz');
insert into pais(cod_pais,nom_pais) values(30,'Bariloche');
insert into pais(cod_pais,nom_pais) values(31,'Tampa');
insert into pais(cod_pais,nom_pais) values(32,'Peel Port');
insert into pais(cod_pais,nom_pais) values(33,'Durban');

insert into cliente values(1,'stiven','perdomo');

select* from barco;

select * from usuario;
select * from tripulante;
select* from tipo_empleado;
select * FROM USUARIO where username='marioherrera' and pass='123456789'