-- username: root
-- pass: 12345

create database TIHC;

use TIHC;

-- tabla cliente
create table cliente(
cod_cliente int  primary key,
nom_cliente varchar(60),
ape_cliente varchar(60)
); 

-- tabla puerto
create table puerto(
cod_puerto int primary key,
nom_puerto varchar(70),
capacidad int
);

-- tabla estado
create table estado(
cod_estado int primary key,
nom_estado varchar(60),
descripcion varchar(400)
);

-- tabla itinerario
create table itinerario(
cod_itinerario int primary key,
descripcion varchar(400),
hora_salida datetime,
hora_llegada datetime
);

-- tipo empleado
create table tipo_empleado(
cod_tipo_empleado int primary key,
nom_tipo_empleado varchar(60)
);

-- tabla empleado
create table empleado(
cod_empleado int primary key,
nom_empleado varchar(60),
ape_empleado varchar(60),
tipo_empleado int,
constraint tipo_empleado_fk foreign key (tipo_empleado) references tipo_empleado(cod_tipo_empleado)   
);

-- tabla carga
create table carga(
cod_carga int primary key,
pedo int,
descripcion varchar(400)
);

-- tabla contrato
create table contrato(
cod_contrato int primary key,
cod_cliente int,
cod_carga int,
cod_puerto int,
origen varchar(60),
destino varchar(60),
constraint cliente_fk foreign key(cod_cliente) references cliente(cod_cliente),
constraint carga_fk foreign key(cod_carga) references carga(cod_carga),
constraint puerto_fk foreign key(cod_puerto) references puerto(cod_puerto)
);

-- tabla barco
create table barco(
cod_barco int primary key,
cod_carga int,
cod_itinerario int,
cod_estado int,
fecha_adquisicion date,
fecha_ultimo_mantenimiento date,
constraint carga2_fk foreign key(cod_carga) references carga(cod_carga),
constraint itinerario_fk foreign key(cod_itinerario) references itinerario(cod_itinerario),
constraint estado_fk foreign key(cod_estado) references estado(cod_estado)
);

-- tabla tripulacion
create table tripulacion(
cod_barco int,
cod_empleado int,
constraint barco_fk foreign key(cod_barco) references barco(cod_barco),
constraint empleado_fk foreign key(cod_empleado) references empleado(cod_empleado)
);
