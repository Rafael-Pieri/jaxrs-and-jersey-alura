package br.com.alura.store.service;

import br.com.alura.store.dto.ProjectDTO;
import br.com.alura.store.exception.EntityNotFoundException;
import br.com.alura.store.mapper.ProjectMapper;
import br.com.alura.store.model.Project;
import br.com.alura.store.repository.ProjectRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class.getName());

    private static final String PROJECT_NOT_FOUND = "Project not found";

    @Inject
    private ProjectRepository projectRepository;

    public ProjectDTO find(Long id) {
        ProjectDTO projectDTO = new ProjectDTO();

        BeanUtils.copyProperties(projectRepository.findOne(id).orElseThrow(() -> {
            LOGGER.info("The Project {} does not exist", id);
            return new EntityNotFoundException(PROJECT_NOT_FOUND);
        }), projectDTO);

        return projectDTO;
    }

    public Collection<ProjectDTO> findAll() {
        Optional<List<Project>> projects = projectRepository.findAll();
        return ProjectMapper.INSTANCE.toProjectList(projects);
    }

    public Project save(ProjectDTO projectDTO) {
        return projectRepository.save(new Project(projectDTO)).orElseThrow(RuntimeException::new);
    }

    public void remove(Long id) {
        if (!projectRepository.exists(id)) {
            LOGGER.error("The Project {} does not exist.", id);
            throw new EntityNotFoundException(PROJECT_NOT_FOUND);
        }

        projectRepository.delete(id);
    }
}