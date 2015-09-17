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

