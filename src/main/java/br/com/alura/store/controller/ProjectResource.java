package br.com.alura.store.controller;

import br.com.alura.store.dto.ProjectDTO;
import br.com.alura.store.dto.ProjectPostDTO;
import br.com.alura.store.model.Project;
import br.com.alura.store.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;

@Api(value = "Project Controller", produces = "application/json")
@Path("/api/projects")
@Component
public class ProjectResource {

    private ProjectService projectService;

    @Inject
    public ProjectResource(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ApiOperation(value = "Gets a hello resource. Version 1 - (version in URL)", response = ProjectDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "hello resource found"),
        @ApiResponse(code = 404, message = "Given admin user not found")
    })
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ProjectDTO find(@PathParam("id") Long id) {
        return projectService.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ProjectDTO> findAll() {
        return projectService.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(ProjectPostDTO projectPostDTO) {
        Project project = projectService.save(projectPostDTO);
        URI uri = URI.create(String.format("/api/projects/%s", project.getId()));
        return Response.created(uri).build();
    }

    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") Long id) {
        projectService.remove(id);
        return Response.noContent().build();
    }
}