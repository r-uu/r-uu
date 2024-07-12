-- Postgres Docker image automatically creates the "staging" database including "staging" role.

-- DROP   DATABASE IF EXISTS staging;
-- CREATE DATABASE           staging;
-- DROP   SCHEMA   IF EXISTS staging;
-- DROP   ROLE     IF EXISTS staging;
-- CREATE ROLE               staging
--	 WITH
--     SUPERUSER
--     CREATEDB
--	   CREATEROLE
--	   INHERIT
--	   LOGIN
--	   REPLICATION
--	   BYPASSRLS
--	   CONNECTION LIMIT -1;
-- CREATE SCHEMA             staging
--   AUTHORIZATION staging;

DROP   DATABASE IF EXISTS demo-staging;
CREATE DATABASE           demo-staging;
DROP   SCHEMA   IF EXISTS demo-staging;
DROP   ROLE     IF EXISTS demo-staging;
CREATE ROLE               demo-staging;
CREATE SCHEMA             demo-staging AUTHORIZATION demo-staging;