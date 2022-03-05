package com.incorta.projects.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.incorta.projects.enums.EmployeeTitle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private EmployeeTitle title;

    @ManyToOne
    @JsonIgnore
    private Department department;

    @ManyToMany(mappedBy = "participants")
    @JsonIgnore
    private List<Project> projects;
}

