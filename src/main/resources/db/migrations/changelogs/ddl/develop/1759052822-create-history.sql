-- liquibase formatted sql

-- changeset Noman5237:1759052822-1
-- state changes
create table if not exists kratos.state_changes
(
    id         uuid                  default uuid_generate_v4() not null primary key,
    from_state varchar(255) not null,
    to_state   varchar(255) not null,
    action     varchar(255) not null,
    wf_id      uuid         not null,

    created_by varchar(255) not null,
    created_at timestamptz  not null default now()
);

-- changeset Noman5237:1759052822-2
-- data changes
create table if not exists kratos.data_changes
(
    id            uuid                  default uuid_generate_v4() not null primary key,
    diff          jsonb        not null,
    current_state varchar(255) not null,
    wf_id         uuid         not null,

    created_by    varchar(255) not null,
    created_at    timestamptz  not null default now()
);
