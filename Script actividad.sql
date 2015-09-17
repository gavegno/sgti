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

