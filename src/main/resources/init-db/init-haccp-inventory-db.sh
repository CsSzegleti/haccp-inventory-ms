#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER haccp_inventory;
    CREATE DATABASE haccp_inventory;
    GRANT ALL PRIVILEGES ON DATABASE haccp_inventory TO haccp_inventory;
EOSQL