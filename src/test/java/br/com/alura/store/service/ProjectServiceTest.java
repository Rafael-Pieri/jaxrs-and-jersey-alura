package br.com.alura.store.service;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.alura.store.dto.ProjectPostDTO;
import br.com.alura.store.exception.EntityNotFoundException;
import br.com.alura.store.model.Project;
import br.com.alura.store.repository.ProjectRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    private static final String PROJECT_NOT_FOUND = "Project not found";
    private static final String JERSEY = "Jersey";
    private static final Integer YEAR_2018 = 2018;
    private static final String UNKNOWN_ERROR = "Unknown error";

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Test
    public void shouldFindProjectById() {
        final Long projectId = 1L;
        final Project project = new Project(projectId, JERSEY, YEAR_2018);

        when(this.projectRepository.findOne(projectId)).thenReturn(Optional.of(project));

        this.projectService.find(projectId);

        verify(this.projectRepository, times(1)).findOne(projectId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findProjectByIdShouldThrowAnException() {
        final Long projectId = 1L;

        when(this.projectRepository.findOne(projectId)).thenThrow(new EntityNotFoundException(PROJECT_NOT_FOUND));

        this.projectService.find(projectId);
    }

    @Test
    public void findAllShouldReturnAListOfProjects() {
        final Project project = new Project(1L, JERSEY, YEAR_2018);

        when(this.projectRepository.findAll()).thenReturn(Optional.of(Collections.singletonList(project)));

        this.projectService.findAll();

        verify(this.projectRepository, times(1)).findAll();
    }

    @Test
    public void shouldSaveProject() {
        final ProjectPostDTO projectPostDTO = new ProjectPostDTO(JERSEY, YEAR_2018);
        final Project project = new Project(1L, JERSEY, YEAR_2018);

        when(this.projectRepository.save(anyObject())).thenReturn(Optional.of(project));

        this.projectService.save(projectPostDTO);

        verify(this.projectRepository, times(1)).save(anyObject());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowAnException() {
        final ProjectPostDTO projectPostDTO = new ProjectPostDTO(JERSEY, YEAR_2018);

        when(this.projectRepository.save(anyObject())).thenThrow(new IllegalArgumentException(UNKNOWN_ERROR));

        this.projectService.save(projectPostDTO);
    }

    @Test
    public void shouldDeleteProject() {
        final Long projectId = 1L;

        when(this.projectRepository.exists(projectId)).thenReturn(true);
        doNothing().when(this.projectRepository).delete(projectId);

        this.projectService.remove(projectId);

        verify(this.projectRepository, times(1)).exists(projectId);
        verify(this.projectRepository, times(1)).delete(projectId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeShouldThrowAnException() {
        final Long projectId = 1L;

        when(this.projectRepository.exists(projectId)).thenReturn(false);

        this.projectService.remove(projectId);
    }
}