-- liquibase formatted sql

-- changeset Noman5237:1758634082-1
-- form create
insert into kratos.resource_operations(resource, operation, display_name, display_description)
values ('form', 'create', 'create form', 'user will have permission to create workflow form')
on conflict (resource, operation) do nothing;

-- changeset Noman5237:1758634082-2
-- form update
insert into kratos.resource_operations(resource, operation, display_name, display_description)
values ('form', 'update', 'update form', 'user will have permission to update workflow form')
on conflict (resource, operation) do nothing;

-- changeset Noman5237:1758634082-3
-- form get
insert into kratos.resource_operations(resource, operation, display_name, display_description)
values ('form', 'get', 'get form', 'user will have permission to get workflow form')
on conflict (resource, operation) do nothing;

-- changeset Noman5237:1758634082-4
-- form delete
insert into kratos.resource_operations(resource, operation, display_name, display_description)
values ('form', 'delete', 'delete form', 'user will have permission to delete workflow form')
on conflict (resource, operation) do nothing;

-- changeset Noman5237:1758634082-5
-- only admin can create form
insert into kratos.resource_roles(app_role_id, resource_operation_id)
values ((select id from kratos.app_roles where app_role = 'ADMIN' and is_predefined),
        (select id from kratos.resource_operations where resource = 'form' and operation = 'create'))
on conflict (app_role_id, resource_operation_id) do nothing;

-- changeset Noman5237:1758634082-6
-- workflow admin can update form
insert into kratos.resource_roles(app_role_id, resource_operation_id)
values ((select id from kratos.app_roles where app_role = 'ADMIN' and is_predefined),
        (select id from kratos.resource_operations where resource = 'form' and operation = 'update'))
on conflict (app_role_id, resource_operation_id) do nothing;

-- changeset Noman5237:1758634082-7
-- workflow admin can get form
insert into kratos.resource_roles(app_role_id, resource_operation_id)
values ((select id from kratos.app_roles where app_role = 'ADMIN' and is_predefined),
        (select id from kratos.resource_operations where resource = 'form' and operation = 'get'))
on conflict (app_role_id, resource_operation_id) do nothing;

-- changeset Noman5237:1758634082-8
-- workflow admin can soft-delete form
insert into kratos.resource_roles(app_role_id, resource_operation_id)
values ((select id from kratos.app_roles where app_role = 'ADMIN' and is_predefined),
        (select id from kratos.resource_operations where resource = 'form' and operation = 'delete'))
on conflict (app_role_id, resource_operation_id) do nothing;
