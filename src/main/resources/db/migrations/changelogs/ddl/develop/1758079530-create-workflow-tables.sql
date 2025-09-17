-- liquibase formatted sql

-- changeset Abbiirr:1758079530-1
-- workflow configurations
create table if not exists kratos.workflow_configurations
(
    id         uuid                  default uuid_generate_v4() not null primary key,
    config     jsonb        not null,

    created_by varchar(255) not null,
    updated_by varchar(255) not null,

    created_at timestamptz  not null default now(),
    updated_at timestamptz  not null default now(),

    is_active  boolean      not null default true
);

-- changeset Abbiirr:1758079530-2
-- workflow runs
create table if not exists kratos.workflows
(
    id           uuid                  default uuid_generate_v4() not null primary key,
    wf_config_id uuid         not null references kratos.workflow_configurations (id),
    data         jsonb        not null,
    state        varchar(255) not null,

    created_by   varchar(100) not null,
    updated_by   varchar(100) not null,

    created_at   timestamptz  not null default now(),
    updated_at   timestamptz  not null default now(),

    is_active    boolean      not null default true
);

-- changeset Abbiirr:1758079530-3
-- configuration state permissions
create table if not exists kratos.workflow_state_permissions
(
    id           uuid                  default uuid_generate_v4() not null primary key,
    wf_config_id uuid         not null references workflow_configurations (id),
    state        varchar(255) not null,
    action       varchar(255) not null,

    created_at   timestamptz  not null default now(),
    updated_at   timestamptz  not null default now()
);

-- changeset Abbiirr:1758079530-4
-- configuration state field permissions
create table if not exists kratos.workflow_field_permissions
(
    id           uuid                  default uuid_generate_v4() not null primary key,
    wf_config_id uuid         not null references kratos.workflow_configurations (id),
    state        varchar(255) not null,
    field        varchar(255) not null,
    action       varchar(255) not null,

    created_at   timestamptz  not null default now(),
    updated_at   timestamptz  not null default now()
);

-- changeset Abbiirr:1758079530-5
-- configuration state permission roles
create table if not exists kratos.workflow_state_permission_roles
(
    wf_role_id             uuid not null references kratos.app_roles (id),
    wf_state_permission_id uuid not null references kratos.workflow_state_permissions (id),
    constraint pk_wf_state_permission_roles primary key (wf_role_id, wf_state_permission_id)
);

-- changeset Abbiirr:1758079530-6
-- configuration state field permission roles
create table if not exists kratos.workflow_field_permission_roles
(
    wf_role_id             uuid not null references kratos.app_roles (id),
    wf_field_permission_id uuid not null references kratos.workflow_field_permissions (id),
    constraint pk_wf_field_permission_roles primary key (wf_role_id, wf_field_permission_id)
);
