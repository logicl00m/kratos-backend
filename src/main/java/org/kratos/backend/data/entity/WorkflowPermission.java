package org.kratos.backend.data.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

// 1. WorkflowPermission
@Entity
@Table(name = "workflow_permissions_matrix")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class WorkflowPermission extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(nullable = false, length = 100)
    private String action;

    @Column(nullable = false, length = 100)
    private String role;

    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY)
    private List<WorkflowAssignee> assignees;
}


