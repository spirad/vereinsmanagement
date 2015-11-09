-- Database: verein

-- DROP DATABASE verein;

CREATE DATABASE verein
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'German_Germany.1252'
       LC_CTYPE = 'German_Germany.1252'
       CONNECTION LIMIT = -1;

COMMENT ON DATABASE verein
  IS 'Verwaltung des Vereins';

