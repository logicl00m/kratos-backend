package org.kratos.backend.data.entity;

// 3. WorkflowConfiguration
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "workflow_configuration")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkflowConfiguration extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workflow_config_id")
    private Long workflowConfigId;

    @Column(name = "config_json", columnDefinition = "jsonb", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode configJson;
}

