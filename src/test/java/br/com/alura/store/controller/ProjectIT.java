package br.com.alura.store.controller;

import br.com.alura.store.dto.ProjectDTO;
import br.com.alura.store.dto.ProjectPostDTO;
import br.com.alura.store.model.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProjectIT {

    private static final String API_PATH = "/api/projects";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String YEAR = "year";
    private static final String LOCATION = "Location";
    private static final String JERSEY = "Jersey";
    private static final String SPRING = "Spring";
    private static final Integer _2018 = 2018;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void findShouldReturnOk() {
        final ProjectPostDTO jerseyProjectDTO = new ProjectPostDTO(JERSEY, _2018);

        final ResponseEntity<Project> jerseyProject = this.restTemplate
                .postForEntity(API_PATH, jerseyProjectDTO, Project.class);

        final String location = getLocation(jerseyProject);

        final ResponseEntity<ProjectDTO> projectFound = this.restTemplate
                .getForEntity(location, ProjectDTO.class);

        assertThat(projectFound.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(projectFound.getBody()).hasFieldOrProperty(ID);
        assertThat(projectFound.getBody()).hasFieldOrProperty(NAME);
        assertThat(projectFound.getBody()).hasFieldOrProperty(YEAR);

        this.restTemplate.delete(location);
    }

    private String getLocation(ResponseEntity<Project> responseEntity) {
        return responseEntity.getHeaders().get(LOCATION).get(0);
    }

    @Test
    public void findAllShouldReturnOk() {
        final ProjectPostDTO jerseyProjectDTO = new ProjectPostDTO(JERSEY, _2018);
        final ProjectPostDTO springProjectDTO = new ProjectPostDTO(SPRING, _2018);

        final ResponseEntity<Project> jerseyProject = this.restTemplate
                .postForEntity(API_PATH, jerseyProjectDTO, Project.class);
        final ResponseEntity<Project> springProject = this.restTemplate
                .postForEntity(API_PATH, springProjectDTO, Project.class);

        final ResponseEntity<ProjectDTO[]> projectFound = this.restTemplate
                .getForEntity(API_PATH, ProjectDTO[].class);

        assertThat(projectFound.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(projectFound.getBody()).hasSize(2);

        this.restTemplate.delete(getLocation(jerseyProject));
        this.restTemplate.delete(getLocation(springProject));
    }

    @Test
    public void createShouldReturnCreated() {
        final ProjectPostDTO jerseyProjectDTO = new ProjectPostDTO(JERSEY, _2018);

        final ResponseEntity<Project> jerseyProject = this.restTemplate
                .postForEntity(API_PATH, jerseyProjectDTO, Project.class);

        assertThat(jerseyProject.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(jerseyProject.getHeaders()).containsKey(LOCATION);
        assertThat(jerseyProject.getBody()).isNull();

        this.restTemplate.delete(getLocation(jerseyProject));
    }

    @Test
    public void deleteShouldReturnNoContent() {
        final ProjectPostDTO jerseyProjectDTO = new ProjectPostDTO(JERSEY, _2018);

        final ResponseEntity<Project> jerseyProject = this.restTemplate
                .postForEntity(API_PATH, jerseyProjectDTO, Project.class);

        final String location = getLocation(jerseyProject);

        final ResponseEntity<Void> response = this.restTemplate
                .exchange(location, HttpMethod.DELETE, RequestEntity.EMPTY, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}