-- liquibase formatted sql

-- changeset Abbiirr:1758647795-1
-- form table
create table if not exists kratos.forms
(
    id         uuid                  default uuid_generate_v4() not null primary key,
    name       varchar(255) not null,
    config     jsonb        not null,

    created_by varchar(255) not null,
    updated_by varchar(255) not null,

    created_at timestamptz  not null default now(),
    updated_at timestamptz  not null default now(),

    is_active  boolean      not null default true,
    is_deleted boolean      not null default false
);

