package org.kratos.backend.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

// 4. Workflow
@Entity
@Table(name = "workflows")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Workflow extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workflow_id")
    private Long workflowId;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "workflow_config_id", nullable = false)
    private Long workflowConfigId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_config_id", insertable = false, updatable = false)
    private WorkflowConfiguration configuration;

    @OneToMany(mappedBy = "workflow", fetch = FetchType.LAZY)
    private List<WorkflowAssignee> assignees;
}


