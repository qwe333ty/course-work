CREATE SEQUENCE client_id_sequence;

CREATE TABLE client
(
  id      NUMERIC(20) DEFAULT nextval('client_id_sequence'::regclass),
  name    VARCHAR(50)  NOT NULL,
  message VARCHAR(100) NOT NULL,

  CONSTRAINT client_primary_key PRIMARY KEY (id)
);