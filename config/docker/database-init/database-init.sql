-- Postgres Docker image automatically creates the "r_uu" database including "r_uu" role based on the values for
-- POSTGRES_DB, POSTGRES_USER and POSTGRES_PASSWORD in docker-compose.yml.

\connect r_uu;

DROP   DATABASE IF EXISTS lib_test;
CREATE DATABASE           lib_test;
\connect                  lib_test;
DROP   SCHEMA   IF EXISTS lib_test;
DROP   ROLE     IF EXISTS lib_test;
CREATE ROLE               lib_test    WITH LOGIN PASSWORD 'lib_test';
CREATE SCHEMA             lib_test    AUTHORIZATION lib_test;

\connect r_uu;

DROP   DATABASE IF EXISTS lib_staging;
CREATE DATABASE           lib_staging;
\connect                  lib_staging;
DROP   SCHEMA   IF EXISTS lib_staging;
DROP   ROLE     IF EXISTS lib_staging;
CREATE ROLE               lib_staging WITH LOGIN PASSWORD 'lib_staging';
CREATE SCHEMA             lib_staging AUTHORIZATION lib_staging;

\connect r_uu;

DROP   DATABASE IF EXISTS app_test;
CREATE DATABASE           app_test;
\connect                  app_test;
DROP   SCHEMA   IF EXISTS app_test;
DROP   ROLE     IF EXISTS app_test;
CREATE ROLE               app_test    WITH LOGIN PASSWORD 'app_test';
CREATE SCHEMA             app_test    AUTHORIZATION app_test;

\connect r_uu;

DROP   DATABASE IF EXISTS app_staging;
CREATE DATABASE           app_staging;
\connect                  app_staging;
DROP   SCHEMA   IF EXISTS app_staging;
DROP   ROLE     IF EXISTS app_staging;
CREATE ROLE               app_staging WITH LOGIN PASSWORD 'app_staging';
CREATE SCHEMA             app_staging AUTHORIZATION app_staging;