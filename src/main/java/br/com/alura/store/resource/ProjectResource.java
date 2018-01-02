package br.com.alura.store.resource;

import br.com.alura.store.model.Project;
import br.com.alura.store.repository.ProjectRepository;
import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
@Path("projects")
public class ProjectResource {

    @Autowired
    private ProjectRepository projectRepository;

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Project find(@PathParam("id") Long id) {
        return projectRepository.findOne(id).get();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Project project) {
        projectRepository.save(project);

        URI uri = URI.create("/projects/" + project.getId());

        return Response.created(uri).build();
    }

    @Path("{id}")
    @DELETE
    public Response removeProject(@PathParam("id") Long id) {
        projectRepository.delete(id);

        return Response.noContent().build();
    }

}
