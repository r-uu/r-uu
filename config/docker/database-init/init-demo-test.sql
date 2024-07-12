-- Postgres Docker image automatically creates the "test" database including "test" role.

--DROP   DATABASE IF EXISTS test;
--CREATE DATABASE           test;
--
--DROP   ROLE IF EXISTS test;
--CREATE ROLE           test WITH
--	SUPERUSER
--	CREATEDB
--	CREATEROLE
--	INHERIT
--	LOGIN
--	REPLICATION
--	BYPASSRLS
--	CONNECTION LIMIT -1;

DROP   DATABASE IF EXISTS demo-test;
CREATE DATABASE           demo-test;
DROP   SCHEMA   IF EXISTS demo-test;
DROP   ROLE     IF EXISTS demo-test;
CREATE ROLE               demo-test;
CREATE SCHEMA             demo-test AUTHORIZATION demo-test;