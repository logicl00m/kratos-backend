#!/bin/bash
set -e
echo "POSTGRES_USER: ${POSTGRES_USER}"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
  create schema if not exists "kratos" authorization "$POSTGRES_USER";
  create schema if not exists "keycloak" authorization "$POSTGRES_USER";
EOSQL
