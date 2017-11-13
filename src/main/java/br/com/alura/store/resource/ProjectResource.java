package br.com.alura.store.resource;

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

import br.com.alura.store.dao.ProjectDAO;
import br.com.alura.store.model.Project;

@Path("projects")
public class ProjectResource {

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Project find(@PathParam("id") Long id) {
		return new ProjectDAO().find(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Project project) {
		new ProjectDAO().add(project);

		URI uri = URI.create("/projects/" + project.getId());

		return Response.created(uri).build();
	}

	@Path("{id}")
	@DELETE
	public Response removeProject(@PathParam("id") Long id) {
		new ProjectDAO().remove(id);

		return Response.noContent().build();
	}

}
