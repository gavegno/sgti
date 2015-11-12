-- Table: usuario

-- DROP TABLE usuario;

CREATE TABLE usuario
(
  id character varying NOT NULL,
  nombre character varying NOT NULL,
  apellido character varying NOT NULL,
  contrasena character varying NOT NULL,
  email character varying,
  telefono character varying,
  tipo character varying,
  activo boolean,
  imei character varying,
  CONSTRAINT usuario_id_primarikey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario
  OWNER TO root;


-- Table: tipohora

-- DROP TABLE tipohora;

CREATE TABLE tipohora
(
  id serial NOT NULL,
  tipo character varying NOT NULL,
  CONSTRAINT "id_primaryKey" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tipohora
  OWNER TO root;


-- Table: usuario_tipohora

-- DROP TABLE usuario_tipohora;

CREATE TABLE usuario_tipohora
(
  id_tipohora integer NOT NULL,
  id_usuario character varying NOT NULL,
  CONSTRAINT "tipohora_usuario_primaryKey" PRIMARY KEY (id_tipohora, id_usuario),
  CONSTRAINT "id_tipohora_foreignKey" FOREIGN KEY (id_tipohora)
      REFERENCES tipohora (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "id_usuario_foreingKey" FOREIGN KEY (id_usuario)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario_tipohora
  OWNER TO root;


-- Table: precio

-- DROP TABLE precio;

CREATE TABLE precio
(
  precio double precision NOT NULL,
  fechadesde date NOT NULL,
  fechahasta date NOT NULL,
  idcontrato character varying NOT NULL,
  precioextra double precision,
  CONSTRAINT "precio_primaryKey" PRIMARY KEY (precio, fechadesde, fechahasta, idcontrato),
  CONSTRAINT "precio_contrato_foreignKey" FOREIGN KEY (idcontrato)
      REFERENCES contrato (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE precio
  OWNER TO root;

-- Index: "fki_precio_contrato_foreignKey"

-- DROP INDEX "fki_precio_contrato_foreignKey";

CREATE INDEX "fki_precio_contrato_foreignKey"
  ON precio
  USING btree
  (idcontrato COLLATE pg_catalog."default");



-- Table: horario

-- DROP TABLE horario;

CREATE TABLE horario
(
  id character varying NOT NULL,
  nombredia character varying NOT NULL,
  horadesde character varying,
  horahasta character varying,
  idcontrato character varying,
  CONSTRAINT "primary" PRIMARY KEY (id, nombredia)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE horario
  OWNER TO root;

-- Index: "fki_horario_contrato_foreignKey"

-- DROP INDEX "fki_horario_contrato_foreignKey";

CREATE INDEX "fki_horario_contrato_foreignKey"
  ON horario
  USING btree
  (idcontrato COLLATE pg_catalog."default");



-- Table: hora

-- DROP TABLE hora;

CREATE TABLE hora
(
  id serial NOT NULL,
  fechahasta date,
  fechadesde date,
  horadesde character varying,
  horahasta character varying,
  remoto boolean,
  usuario character varying,
  contrato character varying,
  fechainformar date,
  fechafacturar date,
  fechacomputar date,
  validada boolean,
  actividad character varying,
  tipohora integer,
  descripcion character varying,
  informada boolean,
  facturada boolean,
  duracion integer,
  comentario character varying,
  CONSTRAINT "hora_primaryKey" PRIMARY KEY (id),
  CONSTRAINT "contrato_hora_foreignKey" FOREIGN KEY (contrato)
      REFERENCES contrato (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "usuario_hora_foreingKey" FOREIGN KEY (usuario)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE hora
  OWNER TO root;


-- Table: contrato_tipohora

-- DROP TABLE contrato_tipohora;

CREATE TABLE contrato_tipohora
(
  idcontrato character varying NOT NULL,
  idtipohora integer NOT NULL,
  computos double precision NOT NULL,
  CONSTRAINT "contrato_tipohora_primeryKey" PRIMARY KEY (computos, idcontrato, idtipohora),
  CONSTRAINT "idTipohora_foreignKey" FOREIGN KEY (idtipohora)
      REFERENCES tipohora (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "tipohora_contrato_foreignKey" FOREIGN KEY (idcontrato)
      REFERENCES contrato (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE contrato_tipohora
  OWNER TO root;

-- Index: "fki_tipohora_contrato_foreignKey"

-- DROP INDEX "fki_tipohora_contrato_foreignKey";

CREATE INDEX "fki_tipohora_contrato_foreignKey"
  ON contrato_tipohora
  USING btree
  (idcontrato COLLATE pg_catalog."default");



-- Table: contrato_tecnico

-- DROP TABLE contrato_tecnico;

CREATE TABLE contrato_tecnico
(
  idcontrato character varying NOT NULL,
  idusuario character varying NOT NULL,
  CONSTRAINT primarykey_contrato_tecnico PRIMARY KEY (idcontrato, idusuario),
  CONSTRAINT foreignkey_contrato_tecnico FOREIGN KEY (idusuario)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE contrato_tecnico
  OWNER TO root;

-- Index: fki_foreignkey_contrato_tecnico

-- DROP INDEX fki_foreignkey_contrato_tecnico;

CREATE INDEX fki_foreignkey_contrato_tecnico
  ON contrato_tecnico
  USING btree
  (idusuario COLLATE pg_catalog."default");

-- Table: contrato

-- DROP TABLE contrato;

CREATE TABLE contrato
(
  id character varying NOT NULL,
  cliente integer,
  contraparte character varying,
  ultimafechainforme date,
  ultimafechafacturacion date,
  ultimafechacomputacion date,
  CONSTRAINT "contrato_primeryKey" PRIMARY KEY (id),
  CONSTRAINT "contrato_cliente_foreignKey" FOREIGN KEY (cliente)
      REFERENCES cliente (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "contrato_contraparte_foreignKey" FOREIGN KEY (contraparte)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE contrato
  OWNER TO root;

-- Index: "fki_contrato_cliente_foreignKey"

-- DROP INDEX "fki_contrato_cliente_foreignKey";

CREATE INDEX "fki_contrato_cliente_foreignKey"
  ON contrato
  USING btree
  (cliente);

-- Index: "fki_contrato_contraparte_foreignKey"

-- DROP INDEX "fki_contrato_contraparte_foreignKey";

CREATE INDEX "fki_contrato_contraparte_foreignKey"
  ON contrato
  USING btree
  (contraparte COLLATE pg_catalog."default");

-- Table: configuracion

-- DROP TABLE configuracion;

CREATE TABLE configuracion
(
  id serial NOT NULL,
  fechainicio date,
  fechafin date,
  renovacion character varying,
  periodorenovacion integer,
  tipocontrato character varying,
  computospaquete integer,
  periodovalidezmes integer,
  periodovalidezdia integer,
  acumulacion boolean,
  periodoacumulacion integer,
  frecuenciainforme integer,
  frecuenciafacturacion integer,
  frecuenciacomputosextra integer,
  tiemporespuesta character varying,
  id_horariolaboral character varying,
  id_contrato character varying,
  CONSTRAINT "configuracion_primaryKey" PRIMARY KEY (id),
  CONSTRAINT "configuracion_contrato_foreignKey" FOREIGN KEY (id_contrato)
      REFERENCES contrato (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE configuracion
  OWNER TO root;

-- Index: "fki_configuracion_contrato_foreignKey"

-- DROP INDEX "fki_configuracion_contrato_foreignKey";

CREATE INDEX "fki_configuracion_contrato_foreignKey"
  ON configuracion
  USING btree
  (id_contrato COLLATE pg_catalog."default");


-- Table: cliente

-- DROP TABLE cliente;

CREATE TABLE cliente
(
  id serial NOT NULL,
  nombre character varying NOT NULL,
  direccion character varying,
  telefono character varying,
  activo boolean NOT NULL,
  CONSTRAINT "cliente_primaryKey" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE cliente
  OWNER TO root;

  

-- Table: actividad

-- DROP TABLE actividad;

CREATE TABLE actividad
(
  tipo character varying,
  fechacreacion date,
  fechaactividad date,
  usuario character varying,
  contrato character varying,
  periodo integer,
  id character varying NOT NULL,
  descripcion character varying,
  estado character varying,
  CONSTRAINT "actividad_primaryKey" PRIMARY KEY (id),
  CONSTRAINT "contrato_actividad_foreignKey" FOREIGN KEY (contrato)
      REFERENCES contrato (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE actividad
  OWNER TO root;

-- Index: "fki_contrato_actividad_foreignKey"

-- DROP INDEX "fki_contrato_actividad_foreignKey";

CREATE INDEX "fki_contrato_actividad_foreignKey"
  ON actividad
  USING btree
  (contrato COLLATE pg_catalog."default");

-- Index: "fki_socio_actividad_foreignKey"

-- DROP INDEX "fki_socio_actividad_foreignKey";

CREATE INDEX "fki_socio_actividad_foreignKey"
  ON actividad
  USING btree
  (usuario COLLATE pg_catalog."default");

=======
-- Database: sgti

-- DROP DATABASE sgti;

CREATE DATABASE sgti
  WITH OWNER = root
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;


-- Table: tipohora

-- DROP TABLE tipohora;

CREATE TABLE tipohora
(
  id serial NOT NULL,
  tipo character varying NOT NULL,
  CONSTRAINT "id_primaryKey" PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tipohora
  OWNER TO root;

-- Table: usuario

-- DROP TABLE usuario;

CREATE TABLE usuario
(
  id character varying NOT NULL,
  nombre character varying NOT NULL,
  apellido character varying NOT NULL,
  contrasena character varying NOT NULL,
  email character varying,
  telefono character varying,
  tipo character varying,
  activo boolean,
  CONSTRAINT usuario_id_primarikey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario
  OWNER TO root;

-- Table: usuario_tipohora

-- DROP TABLE usuario_tipohora;

CREATE TABLE usuario_tipohora
(
  id_tipohora integer NOT NULL,
  id_usuario character varying NOT NULL,
  CONSTRAINT "tipohora_usuario_primaryKey" PRIMARY KEY (id_tipohora , id_usuario ),
  CONSTRAINT "id_tipohora_foreignKey" FOREIGN KEY (id_tipohora)
      REFERENCES tipohora (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "id_usuario_foreingKey" FOREIGN KEY (id_usuario)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario_tipohora
  OWNER TO root;

-- Table: cliente

-- DROP TABLE cliente;

CREATE TABLE cliente
(
  id serial NOT NULL,
  nombre character varying NOT NULL,
  direccion character varying,
  telefono character varying,
  activo boolean NOT NULL,
  CONSTRAINT "cliente_primaryKey" PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE cliente
  OWNER TO root;

-- Table: contrato

-- DROP TABLE contrato;

CREATE TABLE contrato
(
  id character varying NOT NULL,
  cliente integer,
  contraparte character varying,
  ultimafechainforme date,
  ultimafechafacturacion date,
  ultimafechacomputacion date,
  CONSTRAINT "contrato_primeryKey" PRIMARY KEY (id ),
  CONSTRAINT "contrato_cliente_foreignKey" FOREIGN KEY (cliente)
      REFERENCES cliente (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "contrato_contraparte_foreignKey" FOREIGN KEY (contraparte)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE contrato
  OWNER TO root;

-- Index: "fki_contrato_cliente_foreignKey"

-- DROP INDEX "fki_contrato_cliente_foreignKey";

CREATE INDEX "fki_contrato_cliente_foreignKey"
  ON contrato
  USING btree
  (cliente );

-- Index: "fki_contrato_contraparte_foreignKey"

-- DROP INDEX "fki_contrato_contraparte_foreignKey";

CREATE INDEX "fki_contrato_contraparte_foreignKey"
  ON contrato
  USING btree
  (contraparte COLLATE pg_catalog."default" );

-- Table: configuracion

-- DROP TABLE configuracion;

CREATE TABLE configuracion
(
  id serial NOT NULL,
  fechainicio date,
  fechafin date,
  renovacion character varying,
  periodorenovacion integer,
  tipocontrato character varying,
  computospaquete integer,
  periodovalidezmes integer,
  periodovalidezdia integer,
  acumulacion boolean,
  periodoacumulacion integer,
  frecuenciainforme integer,
  frecuenciafacturacion integer,
  frecuenciacomputosextra integer,
  tiemporespuesta character varying,
  id_horariolaboral character varying,
  id_contrato character varying,
  CONSTRAINT "configuracion_primaryKey" PRIMARY KEY (id ),
  CONSTRAINT "configuracion_contrato_foreignKey" FOREIGN KEY (id_contrato)
      REFERENCES contrato (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE configuracion
  OWNER TO root;

-- Index: "fki_configuracion_contrato_foreignKey"

-- DROP INDEX "fki_configuracion_contrato_foreignKey";

CREATE INDEX "fki_configuracion_contrato_foreignKey"
  ON configuracion
  USING btree
  (id_contrato COLLATE pg_catalog."default" );

-- Table: horario

-- DROP TABLE horario;

CREATE TABLE horario
(
  id character varying NOT NULL,
  nombredia character varying NOT NULL,
  horadesde character varying,
  horahasta character varying,
  idcontrato character varying,
  CONSTRAINT "horario_primaryKey" PRIMARY KEY (nombredia , id ),
  CONSTRAINT "horario_contrato_foreignKey" FOREIGN KEY (idcontrato)
      REFERENCES contrato (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE horario
  OWNER TO root;

-- Index: "fki_horario_contrato_foreignKey"

-- DROP INDEX "fki_horario_contrato_foreignKey";

CREATE INDEX "fki_horario_contrato_foreignKey"
  ON horario
  USING btree
  (idcontrato COLLATE pg_catalog."default" );

﻿-- Table: precio

-- DROP TABLE precio;

CREATE TABLE precio
(
  precio double precision NOT NULL,
  fechadesde date NOT NULL,
  fechahasta date NOT NULL,
  idcontrato character varying NOT NULL,
  precioextra double precision,
  CONSTRAINT "precio_primaryKey" PRIMARY KEY (precio, fechadesde, fechahasta, idcontrato),
  CONSTRAINT "precio_contrato_foreignKey" FOREIGN KEY (idcontrato)
      REFERENCES contrato (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE precio
  OWNER TO root;

-- Index: "fki_precio_contrato_foreignKey"

-- DROP INDEX "fki_precio_contrato_foreignKey";

CREATE INDEX "fki_precio_contrato_foreignKey"
  ON precio
  USING btree
  (idcontrato COLLATE pg_catalog."default");

﻿-- Table: contrato_tipohora

-- DROP TABLE contrato_tipohora;

CREATE TABLE contrato_tipohora
(
  idcontrato character varying NOT NULL,
  idtipohora integer NOT NULL,
  computos double precision NOT NULL,
  CONSTRAINT "contrato_tipohora_primeryKey" PRIMARY KEY (computos, idcontrato, idtipohora),
  CONSTRAINT "idTipohora_foreignKey" FOREIGN KEY (idtipohora)
      REFERENCES tipohora (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "tipohora_contrato_foreignKey" FOREIGN KEY (idcontrato)
      REFERENCES contrato (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE contrato_tipohora
  OWNER TO root;

-- Index: "fki_tipohora_contrato_foreignKey"

-- DROP INDEX "fki_tipohora_contrato_foreignKey";

CREATE INDEX "fki_tipohora_contrato_foreignKey"
  ON contrato_tipohora
  USING btree
  (idcontrato COLLATE pg_catalog."default");

﻿-- Table: actividad

-- DROP TABLE actividad;

CREATE TABLE actividad
(
  tipo character varying,
  fechacreacion date,
  fechaactividad date,
  usuario character varying,
  contrato character varying,
  periodo integer,
  id character varying NOT NULL,
  descripcion character varying,
  CONSTRAINT "actividad_primaryKey" PRIMARY KEY (id),
  CONSTRAINT "contrato_actividad_foreignKey" FOREIGN KEY (contrato)
      REFERENCES contrato (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE actividad
  OWNER TO root;

-- Index: "fki_contrato_actividad_foreignKey"

-- DROP INDEX "fki_contrato_actividad_foreignKey";

CREATE INDEX "fki_contrato_actividad_foreignKey"
  ON actividad
  USING btree
  (contrato COLLATE pg_catalog."default");

-- Index: "fki_socio_actividad_foreignKey"

-- DROP INDEX "fki_socio_actividad_foreignKey";

CREATE INDEX "fki_socio_actividad_foreignKey"
  ON actividad
  USING btree
  (usuario COLLATE pg_catalog."default");

-- Table: hora

-- DROP TABLE hora;

CREATE TABLE hora
(
  id serial NOT NULL,
  fechahasta date,
  fechadesde date,
  horadesde character varying,
  horahasta character varying,
  remoto boolean,
  usuario character varying,
  contrato character varying,
  fechainformar date,
  fechafacturar date,
  fechacomputar date,
  validada boolean,
  actividad character varying,
  tipohora integer,
  descripcion character varying,
  informada boolean,
  facturada boolean,
  duracion integer,
  comentario character varying,
  CONSTRAINT "hora_primaryKey" PRIMARY KEY (id),
  CONSTRAINT "contrato_hora_foreignKey" FOREIGN KEY (contrato)
      REFERENCES contrato (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "usuario_hora_foreingKey" FOREIGN KEY (usuario)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE hora
  OWNER TO root;

-- Table: computo_acumulado

-- DROP TABLE computo_acumulado;

CREATE TABLE computo_acumulado
(
  id serial NOT NULL,
  idcontrato character varying,
  fecha date,
  computos double precision,
  CONSTRAINT "computo_acumulado_primaryKey" PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE computo_acumulado
  OWNER TO root;

