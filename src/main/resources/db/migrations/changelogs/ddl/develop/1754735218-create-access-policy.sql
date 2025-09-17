-- liquibase formatted sql

-- changeset Noman5237:1754735218-1
-- app roles
create table if not exists kratos.app_roles
(
    id            uuid    default uuid_generate_v4() not null
        primary key,
    app_role      varchar(255)                       not null,
    is_predefined boolean default false              not null
);

-- changeset Noman5237:1754735218-2
-- resource operations
create table if not exists kratos.resource_operations
(
    id                  uuid default uuid_generate_v4() not null
        primary key,
    resource            varchar(255)                    not null,
    operation           varchar(255)                    not null,
    display_name        varchar(255)                    not null,
    display_description varchar(255),
    constraint uq_resource_operations
        unique (resource, operation)
);

-- changeset Noman5237:1754735218-3
-- resource roles
create table if not exists kratos.resource_roles
(
    app_role_id           uuid not null
        references kratos.app_roles,
    resource_operation_id uuid not null
        references kratos.resource_operations,
    constraint pk_resource_roles
        primary key (app_role_id, resource_operation_id)
);

-- changeset Noman5237:1754735218-4
-- subject roles
create table if not exists kratos.subject_roles
(
    user_id  varchar(36)          not null,
    subject  uuid                 not null,
    app_role uuid                 not null
        references kratos.app_roles,
    status   boolean default true not null,
    constraint pk_subject_roles
        primary key (user_id, subject, app_role)
);
