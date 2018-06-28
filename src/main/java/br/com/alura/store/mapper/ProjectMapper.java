package br.com.alura.store.mapper;

import br.com.alura.store.dto.ProjectDTO;
import br.com.alura.store.model.Project;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectMapper {

    public static final ProjectMapper INSTANCE = new ProjectMapper();

    private ProjectMapper() {

    }

    public Collection<ProjectDTO> toProjectList(Optional<List<Project>> projects) {
        return projects.orElseGet(ArrayList::new)
                .stream().map(project -> {
                    ProjectDTO projectDTO = new ProjectDTO();
                    BeanUtils.copyProperties(project, projectDTO);
                    return projectDTO;
                }).collect(Collectors.toList());
    }
}