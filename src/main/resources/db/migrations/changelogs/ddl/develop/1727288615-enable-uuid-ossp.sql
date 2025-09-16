-- liquibase formatted sql

-- changeset Noman5237:1727288615-1
-- enable uuid-ossp extension
create
    extension if not exists "uuid-ossp";
