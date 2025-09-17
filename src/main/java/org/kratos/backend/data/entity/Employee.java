package org.kratos.backend.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

// 5. Employee
@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false, unique = true, length = 100)
    private String employeeId;

    @Column(nullable = false, length = 100)
    private String role;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(name = "department", nullable = false, length = 100)
    private String department;

    @Column(name = "manager")
    private Long managerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager", insertable = false, updatable = false)
    private Employee manager;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Employee> directReports;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<WorkflowAssignee> assignments;
}

