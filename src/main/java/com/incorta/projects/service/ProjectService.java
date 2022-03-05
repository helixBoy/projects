package com.incorta.projects.service;

import com.incorta.projects.dto.*;
import com.incorta.projects.entity.*;
import com.incorta.projects.enums.EmployeeTitle;
import com.incorta.projects.enums.ProjectState;
import com.incorta.projects.exception.*;
import com.incorta.projects.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    public Project saveProject(ProjectDto projectDto) {

        // get the owner of the project from the employee table
        // if the owner id is not valid, throw an exception
        Employee owner = employeeRepository.findById(projectDto.getOwnerId()).orElseThrow(() ->
            new ResourceNotFoundException("Owner", "Id", projectDto.getOwnerId()));

        // if the owner is not a manager, throw an exception
        if(!owner.getTitle().equals(EmployeeTitle.MANAGER))
            throw new InvalidRequestException("The owner of the project must be a manager");

        // create a new project
        Project project = new Project();

        // check if the state is valid
        ProjectState state = null;
        try {
            state = ProjectState.valueOf(projectDto.getState().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("The project state must be one of these values: PLANNED, ACTIVE, DONE, FAILED");
        }

        // set the new values of the project
        project.setState(state);
        project.setName(projectDto.getName());
        project.setProgress(projectDto.getProgress());
        project.setOwner(owner);

        // save the project
        return projectRepository.save(project);
    }

    public Project updateProject(long id, ProjectDto projectDto) {
        // get the project to be updated
        Project project = getProjectById(id);

        // if the owner id is updated, then get the new owner
        if ( projectDto.getOwnerId() != null && project.getOwner().getId() != projectDto.getOwnerId()) {
            Employee owner = employeeRepository.findById(projectDto.getOwnerId()).orElseThrow(() ->
                    new ResourceNotFoundException("Owner", "Id", projectDto.getOwnerId()));
            project.setOwner(owner);
        }

        // check if the state is valid
        ProjectState state = null;
        try {
            if (projectDto.getState() != null)
                state = ProjectState.valueOf(projectDto.getState().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("The project state must be one of these values: PLANNED, ACTIVE, DONE, FAILED");
        }

        // set the new values of the project
        project.setState(state);
        project.setName(projectDto.getName());
        project.setProgress( state.equals(ProjectState.ACTIVE) ? projectDto.getProgress(): null);

        // update the project
        return projectRepository.save(project);
    }

    public Project getProjectById(long id) {
        return projectRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Project", "Id", id));
    }

    public void assignParticipants(ProjectParticipantDto projectParticipantDto) {
        Project project = getProjectById(projectParticipantDto.getProject_id());
        Employee participant = employeeRepository.findById(projectParticipantDto.getParticipant_id()).orElseThrow(() ->
                new ResourceNotFoundException("Employee", "Id", projectParticipantDto.getParticipant_id()));

        // check if the participant and the owner are in the same department
        if(participant.getDepartment().getId() != project.getOwner().getDepartment().getId())
            throw new InvalidRequestException("The participant department is different from this project owner department");

        // assign the participant to the project
        project.getParticipants().add(participant);
        projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

}
