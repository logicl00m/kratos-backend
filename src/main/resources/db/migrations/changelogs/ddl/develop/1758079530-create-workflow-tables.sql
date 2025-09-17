CREATE TABLE workflow_permissions_matrix (
    permission_id    BIGSERIAL PRIMARY KEY,
    state            VARCHAR(100) NOT NULL,
    action           VARCHAR(100) NOT NULL,
    role             VARCHAR(100) NOT NULL,
    created_by       VARCHAR(100) DEFAULT CURRENT_USER,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by       VARCHAR(100) DEFAULT CURRENT_USER,
    updated_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_active        BOOLEAN NOT NULL DEFAULT TRUE
);




CREATE TABLE field_permission_matrix (
    field_id         BIGSERIAL PRIMARY KEY,
    field_name       VARCHAR(100) NOT NULL,
    state            VARCHAR(100) NOT NULL,
    field_action     VARCHAR(100) NOT NULL,
    role             VARCHAR(100) NOT NULL,
    created_by       VARCHAR(100) DEFAULT CURRENT_USER,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by       VARCHAR(100) DEFAULT CURRENT_USER,
    updated_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_active        BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE workflow_configuration (
    workflow_config_id BIGSERIAL PRIMARY KEY,
    config_json        JSONB NOT NULL,
    created_by         VARCHAR(100) DEFAULT CURRENT_USER,
    created_at         TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by         VARCHAR(100) DEFAULT CURRENT_USER,
    updated_at         TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_active          BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE workflows (
    workflow_id        BIGSERIAL PRIMARY KEY,
    workflow_config_id BIGINT NOT NULL,
    status             VARCHAR(50) NOT NULL,
    created_by         VARCHAR(100) DEFAULT CURRENT_USER,
    created_at         TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by         VARCHAR(100) DEFAULT CURRENT_USER,
    updated_at         TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_active          BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (workflow_config_id) REFERENCES workflow_configuration(workflow_config_id)
);

CREATE TABLE employees (
    id             BIGSERIAL PRIMARY KEY,
    employee_id    VARCHAR(100) NOT NULL UNIQUE,
    role           VARCHAR(100) NOT NULL,
    name           VARCHAR(200) NOT NULL,
    manager        BIGINT,
    department     VARCHAR(100) NOT NULL,
    created_by     VARCHAR(100) DEFAULT CURRENT_USER,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by     VARCHAR(100) DEFAULT CURRENT_USER,
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_active      BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (manager) REFERENCES employees(id)
);

CREATE TABLE workflow_assignee (
    id                 BIGSERIAL PRIMARY KEY,
    workflow_id        BIGINT NOT NULL,
    permission_id      BIGINT NOT NULL,
    assignee           VARCHAR(100) NOT NULL,
    created_by         VARCHAR(100) DEFAULT CURRENT_USER,
    created_at         TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by         VARCHAR(100) DEFAULT CURRENT_USER,
    updated_at         TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_active          BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (permission_id) REFERENCES workflow_permissions_matrix(permission_id),
    FOREIGN KEY (workflow_id) REFERENCES workflows(workflow_id),
    FOREIGN KEY (assignee) REFERENCES employees(employee_id),
    UNIQUE (workflow_id, permission_id, assignee)
);


-- 1. INDEXES ON FOREIGN KEY COLUMNS

-- workflow_assignee.workflow_id
CREATE INDEX idx_workflow_assignee_workflow_id
    ON workflow_assignee (workflow_id);

-- workflow_assignee.permission_id
CREATE INDEX idx_workflow_assignee_permission_id
    ON workflow_assignee (permission_id);

-- workflow_assignee.assignee (references employees.employee_id)
CREATE INDEX idx_workflow_assignee_assignee
    ON workflow_assignee (assignee);

-- workflows.workflow_config_id
CREATE INDEX idx_workflows_workflow_config_id
    ON workflows (workflow_config_id);

-- employees.manager (self-reference)
CREATE INDEX idx_employees_manager
    ON employees (manager);

-- You could also consider indexing employees.department if commonly used in filters:
CREATE INDEX idx_employees_department
    ON employees (department);


-- 2. INDEXES ON AUDIT TIMESTAMPS (OPTIONAL, USAGE DEPENDENT)

-- workflow_assignee.created_at
CREATE INDEX idx_workflow_assignee_created_at
    ON workflow_assignee (created_at);

-- workflows.updated_at
CREATE INDEX idx_workflows_updated_at
    ON workflows (updated_at);

-- employees.is_active (especially if frequently filtering active employees)
CREATE INDEX idx_employees_is_active
    ON employees (is_active);
