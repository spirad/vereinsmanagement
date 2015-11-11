-- Database: verein


CREATE USER verein_manager WITH PASSWORD 'verein';

-- DROP DATABASE verein;

CREATE DATABASE verein
  WITH OWNER = verein_manager
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       CONNECTION LIMIT = -1;

COMMENT ON DATABASE verein
  IS 'Verwaltung des Vereins';

GRANT ALL PRIVILEGES ON DATABASE verein to verein_manager;

\connect verein

CREATE SEQUENCE mandatid
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 110
  CACHE 1;
ALTER TABLE mandatid
  OWNER TO verein_manager;


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
  OWNER TO verein_manager;

CREATE TABLE appinfo
(
  version text NOT NULL,
  info text NOT NULL,
  date text NOT NULL,
  comment text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE appinfo
  OWNER TO verein_manager;
