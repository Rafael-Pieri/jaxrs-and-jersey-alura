package br.com.alura.store.resource;

import br.com.alura.store.dto.ProjectDTO;
import br.com.alura.store.exception.EntityNotFoundException;
import br.com.alura.store.mapper.ProjectMapper;
import br.com.alura.store.model.Project;
import br.com.alura.store.repository.ProjectRepository;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Path("/api/projects")
@Component
public class ProjectResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectResource.class.getName());
    private static final String PROJECT_NOT_FOUND = "Project not found";

    @Inject
    private ProjectRepository projectRepository;

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ProjectDTO find(@PathParam("id") Long id) {
        ProjectDTO projectDTO = new ProjectDTO();

        BeanUtils.copyProperties(projectRepository.findOne(id).orElseThrow(() -> {
            LOGGER.info("The Project " + id.toString() + " does not exist");
            return new EntityNotFoundException(PROJECT_NOT_FOUND);
        }), projectDTO);

        return projectDTO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ProjectDTO> findAll() {
        Optional<List<Project>> projects = projectRepository.findAll();
        return ProjectMapper.INSTANCE.toProjectList(projects);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(ProjectDTO projectDTO) {
        Project project = new Project(projectDTO);

        projectRepository.save(project).orElseThrow(RuntimeException::new);

        URI uri = URI.create("/api/projects/" + project.getId());

        return Response.created(uri).build();
    }

    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") Long id) {
        if (!projectRepository.exists(id)) {
            LOGGER.error("The Project {} does not exist.", id);
            throw new EntityNotFoundException(PROJECT_NOT_FOUND);
        }

        projectRepository.delete(id);

        return Response.noContent().build();
    }

}
