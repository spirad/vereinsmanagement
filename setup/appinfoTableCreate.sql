-- Table: appinfo

-- DROP TABLE appinfo;

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
  OWNER TO postgres;
