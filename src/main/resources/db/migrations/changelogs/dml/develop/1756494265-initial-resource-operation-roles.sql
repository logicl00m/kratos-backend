-- liquibase formatted sql

-- changeset Noman5237:1756494265-1
-- add admin role
insert into kratos.app_roles (app_role, is_predefined)
select 'ADMIN', true
where not exists (select 1
                  from kratos.app_roles
                  where app_role = 'ADMIN');

-- changeset Noman5237:1756494265-2
-- add workflow admin
insert into kratos.app_roles (app_role, is_predefined)
select 'WORKFLOW_ADMIN', true
where not exists (select 1
                  from kratos.app_roles
                  where app_role = 'WORKFLOW_ADMIN');

-- changeset Noman5237:1756494265-3
-- configuration create
insert into kratos.resource_operations(resource, operation, display_name, display_description)
values ('configuration', 'create', 'create configuration', 'user will have permission to create workflow configuration')
on conflict (resource, operation) do nothing;

-- changeset Noman5237:1756494265-4
-- configuration update
insert into kratos.resource_operations(resource, operation, display_name, display_description)
values ('configuration', 'update', 'update configuration', 'user will have permission to update workflow configuration')
on conflict (resource, operation) do nothing;

-- changeset Noman5237:1756494265-5
-- configuration get
insert into kratos.resource_operations(resource, operation, display_name, display_description)
values ('configuration', 'get', 'get configuration', 'user will have permission to get workflow configuration')
on conflict (resource, operation) do nothing;

-- changeset Noman5237:1756494265-6
-- configuration delete
insert into kratos.resource_operations(resource, operation, display_name, display_description)
values ('configuration', 'delete', 'delete configuration', 'user will have permission to delete workflow configuration')
on conflict (resource, operation) do nothing;

-- changeset Noman5237:1756494265-7
-- workflow create
insert into kratos.resource_operations(resource, operation, display_name, display_description)
values ('workflow', 'create', 'create workflow', 'user will have permission to create workflow from configuration')
on conflict (resource, operation) do nothing;

-- changeset Noman5237:1756494265-8
-- workflow update
insert into kratos.resource_operations(resource, operation, display_name, display_description)
values ('workflow', 'update', 'update workflow', 'user will have permission to update workflow configuration')
on conflict (resource, operation) do nothing;

-- changeset Noman5237:1756494265-9
-- workflow get
insert into kratos.resource_operations(resource, operation, display_name, display_description)
values ('workflow', 'get', 'get workflow', 'user will have permission to get workflow')
on conflict (resource, operation) do nothing;

-- changeset Noman5237:1756494265-10
-- only admin can create workflow
insert into kratos.resource_roles(app_role_id, resource_operation_id)
values ((select id from kratos.app_roles where app_role = 'ADMIN' and is_predefined),
        (select id from kratos.resource_operations where resource = 'configuration' and operation = 'create'))
on conflict (app_role_id, resource_operation_id) do nothing;

-- changeset Noman5237:1756494265-11
-- workflow admin can update workflow
insert into kratos.resource_roles(app_role_id, resource_operation_id)
values ((select id from kratos.app_roles where app_role = 'WORKFLOW_ADMIN' and is_predefined),
        (select id from kratos.resource_operations where resource = 'configuration' and operation = 'update'))
on conflict (app_role_id, resource_operation_id) do nothing;

-- changeset Noman5237:1756494265-12
-- workflow admin can get configuration
insert into kratos.resource_roles(app_role_id, resource_operation_id)
values ((select id from kratos.app_roles where app_role = 'WORKFLOW_ADMIN' and is_predefined),
        (select id from kratos.resource_operations where resource = 'configuration' and operation = 'get'))
on conflict (app_role_id, resource_operation_id) do nothing;

-- changeset Noman5237:1756494265-13
-- workflow admin can soft-delete configuration
insert into kratos.resource_roles(app_role_id, resource_operation_id)
values ((select id from kratos.app_roles where app_role = 'WORKFLOW_ADMIN' and is_predefined),
        (select id from kratos.resource_operations where resource = 'configuration' and operation = 'delete'))
on conflict (app_role_id, resource_operation_id) do nothing;

-- changeset Noman5237:1756494265-14
-- workflow admin can create workflow from configuration
insert into kratos.resource_roles(app_role_id, resource_operation_id)
values ((select id from kratos.app_roles where app_role = 'WORKFLOW_ADMIN' and is_predefined),
        (select id from kratos.resource_operations where resource = 'workflow' and operation = 'create'))
on conflict (app_role_id, resource_operation_id) do nothing;

-- changeset Noman5237:1756494265-15
-- workflow admin can update workflow by configuration
insert into kratos.resource_roles(app_role_id, resource_operation_id)
values ((select id from kratos.app_roles where app_role = 'WORKFLOW_ADMIN' and is_predefined),
        (select id from kratos.resource_operations where resource = 'workflow' and operation = 'update'))
on conflict (app_role_id, resource_operation_id) do nothing;

-- changeset Noman5237:1756494265-16
-- workflow admin can get workflow by configuration
insert into kratos.resource_roles(app_role_id, resource_operation_id)
values ((select id from kratos.app_roles where app_role = 'WORKFLOW_ADMIN' and is_predefined),
        (select id from kratos.resource_operations where resource = 'workflow' and operation = 'get'))
on conflict (app_role_id, resource_operation_id) do nothing;

-- changeset Noman5237:1756494265-17
-- permission get
insert into kratos.resource_operations(resource, operation, display_name, display_description)
values ('permission', 'get', 'get permission', 'user will have permission to get the permissions of configuration')
on conflict (resource, operation) do nothing;

-- changeset Noman5237:1756494265-18
-- workflow admin can get workflow by configuration
insert into kratos.resource_roles(app_role_id, resource_operation_id)
values ((select id from kratos.app_roles where app_role = 'WORKFLOW_ADMIN' and is_predefined),
        (select id from kratos.resource_operations where resource = 'permission' and operation = 'get'))
on conflict (app_role_id, resource_operation_id) do nothing;
