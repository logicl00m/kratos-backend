package org.kratos.backend.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 6. WorkflowAssignee
@Entity
@Table(name = "workflow_assignee", uniqueConstraints = {
        @UniqueConstraint(name = "uk_workflow_perm_assignee", columnNames = {"workflow_id", "permission_id", "assignee"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkflowAssignee extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workflow_id", nullable = false)
    private Long workflowId;

    @Column(name = "permission_id", nullable = false)
    private Long permissionId;

    // Assignee refers to employee.employee_id
    @Column(name = "assignee", nullable = false, length = 100)
    private String assigneeEmployeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", insertable = false, updatable = false)
    private WorkflowPermission permission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", insertable = false, updatable = false)
    private Workflow workflow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee", referencedColumnName = "employee_id", insertable = false, updatable = false)
    private Employee employee;
}
