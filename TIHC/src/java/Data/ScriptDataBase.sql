-- username: root
-- pass: 12345
-- drop database TIHC;
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
ubicaion_y float
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
origen int,
destino int,
constraint cliente_fk foreign key(cod_cliente) references cliente(cod_cliente),
constraint origen_fk foreign key(origen) references pais(cod_pais),
constraint destino_fk foreign key(destino) references pais(cod_pais)
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
descripcion varchar(400),
constraint tipo_carga_fk foreign key(cod_tipo_carga) references tipo_carga(cod_tipo_carga),
constraint barco2_fk foreign key(cod_barco) references barco(cod_barco),
constraint contrato_fk foreign key(cod_contrato) references contrato(cod_contrato)
);


-- tabla itinerario
-- create table itinerario(
-- cod_itinerario int primary key,
-- descripcion varchar(400),
-- hora_salida datetime,
-- hora_llegada datetime
-- );


-- tabla tripulacion
create table tripulacion(
cod_barco int,
cod_empleado int,
constraint barco_fk foreign key(cod_barco) references barco(cod_barco),
constraint empleado_fk foreign key(cod_empleado) references empleado(cod_empleado)
);
