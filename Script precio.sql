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

