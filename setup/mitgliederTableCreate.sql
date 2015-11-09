-- Table: mitglieder

-- DROP TABLE mitglieder;

CREATE TABLE mitglieder
(
  mandat integer NOT NULL DEFAULT nextval('mandatid'::regclass),
  name character varying(40) NOT NULL,
  vorname character varying(40),
  titel character varying(40),
  geschlecht character varying(10),
  beitrag numeric(6,2) NOT NULL,
  monat integer,
  eintritt integer NOT NULL,
  strasse character varying(40),
  stadt character varying(40),
  status character varying(40),
  bemerkung character varying(100),
  CONSTRAINT mitglieder_pkey PRIMARY KEY (mandat),
  CONSTRAINT uc_personid UNIQUE (name, vorname),
  CONSTRAINT mitglieder_name_check CHECK (name::text <> ''::text)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mitglieder
  OWNER TO postgres;
