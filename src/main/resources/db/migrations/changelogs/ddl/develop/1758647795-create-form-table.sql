-- liquibase formatted sql

-- changeset Abbiirr:1758647795-1
-- form table (same structure as workflow_configurations)
create table if not exists kratos.form
(
    id         uuid                  default uuid_generate_v4() not null primary key,
    config     jsonb        not null,

    created_by varchar(255) not null,
    updated_by varchar(255) not null,

    created_at timestamptz  not null default now(),
    updated_at timestamptz  not null default now(),

    is_active  boolean      not null default true,
    is_deleted boolean      not null default false
);


-- liquibase formatted sql
-- changeset Abbiirr:1758647795-2
-- insert form resource operations
insert into kratos.resource_operations (id, resource, operation, display_name, created_at, updated_at)
values ('73108d6b-9925-41ff-8cd0-8406762fa413', 'form', 'get',    'user will have permission to get workflow', now(), now());
insert into kratos.resource_operations (id, resource, operation, description, created_at, updated_at)
values ('f163c3bf-d00d-47d8-a0fd-74ceeb21661d', 'form', 'delete', 'user will have permission to get workflow', now(), now());
insert into kratos.resource_operations (id, resource, operation, description, created_at, updated_at)
values ('404339df-e7df-440c-9242-4c18ecca0be0', 'form', 'create', 'user will have permission to get workflow', now(), now());
insert into kratos.resource_operations (id, resource, operation, description, created_at, updated_at)
values ('7b15d4d4-2d08-4990-8fa7-341744ac9b4e', 'form', 'update', 'user will have permission to get workflow', now(), now());

-- liquibase formatted sql
-- changeset Abbiirr:1758647795-3
-- insert role -> resource_operation mappings
insert into kratos.resource_roles (app_role_id, resource_operation_id)
values ('d77d7b54-c804-4d16-95d5-bc699c054b3f','7b15d4d4-2d08-4990-8fa7-341744ac9b4e');
insert into kratos.resource_roles (app_role_id, resource_operation_id)
values ('d77b9a74-005e-4cf6-af73-02f4aedb26c8','404339df-e7df-440c-9242-4c18ecca0be0');
insert into kratos.resource_roles (app_role_id, resource_operation_id)
values ('d77b9a74-005e-4cf6-af73-02f4aedb26c8','7b15d4d4-2d08-4990-8fa7-341744ac9b4e');
insert into kratos.resource_roles (app_role_id, resource_operation_id)
values ('d77b9a74-005e-4cf6-af73-02f4aedb26c8','73108d6b-9925-41ff-8cd0-8406762fa413');
insert into kratos.resource_roles (app_role_id, resource_operation_id)
values ('d77b9a74-005e-4cf6-af73-02f4aedb26c8','f163c3bf-d00d-47d8-a0fd-74ceeb21661d');
