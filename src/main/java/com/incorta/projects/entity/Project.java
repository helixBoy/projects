package com.incorta.projects.entity;

import com.incorta.projects.enums.EmployeeTitle;
import com.incorta.projects.enums.ProjectState;
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
public class Project {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProjectState state;
    private Double progress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Employee owner;

    @ManyToMany
    @JoinTable(
            name = "project_participant",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id", referencedColumnName = "id"))
    private List<Employee> participants;

    public void setName(String name) {
        if(name != null)
            this.name = name;
    }

    public void setOwner(Employee owner) {
        if(owner != null)
            this.owner = owner;
    }

    public void setProgress(Double progress) {
        this.progress = this.state.equals(ProjectState.ACTIVE) ? progress: null;
        if(this.progress != null && (this.progress < 0.0 || this.progress > 100.0))
            this.progress = 0.0;
    }

}
