package org.kratos.backend.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


// 2. FieldPermission
@Entity
@Table(name = "field_permission_matrix")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FieldPermission extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "field_id")
    private Long fieldId;

    @Column(name = "field_name", nullable = false, length = 100)
    private String fieldName;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(name = "field_action", nullable = false, length = 100)
    private String fieldAction;

    @Column(nullable = false, length = 100)
    private String role;
}

