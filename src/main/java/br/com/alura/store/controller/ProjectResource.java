package br.com.alura.store.controller;

import br.com.alura.store.dto.ProjectDTO;
import br.com.alura.store.dto.ProjectPostDTO;
import br.com.alura.store.model.Project;
import br.com.alura.store.service.ProjectService;
import java.net.URI;
import java.util.Collection;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Component;

@Path("/api/projects")
@Component
public class ProjectResource {

    private ProjectService projectService;

    @Inject
    public ProjectResource(ProjectService projectService) {
        this.projectService = projectService;
    }

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