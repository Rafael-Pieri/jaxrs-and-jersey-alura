package br.com.alura.store.service;

import br.com.alura.store.dto.ProjectDTO;
import br.com.alura.store.dto.ProjectPostDTO;
import br.com.alura.store.exception.EntityNotFoundException;
import br.com.alura.store.mapper.ProjectMapper;
import br.com.alura.store.model.Project;
import br.com.alura.store.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class ProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class.getName());

    private static final String THE_PROJECT_DOES_NOT_EXIST = "The Project {} does not exist";
    private static final String PROJECT_NOT_FOUND = "Project not found";

    private ProjectRepository projectRepository;

    @Inject
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectDTO find(Long id) {
        ProjectDTO projectDTO = new ProjectDTO();

        BeanUtils.copyProperties(projectRepository.findOne(id).orElseThrow(() -> {
            LOGGER.info(THE_PROJECT_DOES_NOT_EXIST, id);
            return new EntityNotFoundException(PROJECT_NOT_FOUND);
        }), projectDTO);

        return projectDTO;
    }

    public Collection<ProjectDTO> findAll() {
        Optional<List<Project>> projects = projectRepository.findAll();
        return ProjectMapper.INSTANCE.toProjectList(projects);
    }

    @Transactional
    public Project save(ProjectPostDTO projectPostDTO) {
        Project project = new Project();
        BeanUtils.copyProperties(projectPostDTO, project);
        return projectRepository.save(project).orElseThrow(RuntimeException::new);
    }

    public void remove(Long id) {
        if (!projectRepository.exists(id)) {
            LOGGER.error(THE_PROJECT_DOES_NOT_EXIST, id);
            throw new EntityNotFoundException(PROJECT_NOT_FOUND);
        }

        projectRepository.delete(id);
    }
}