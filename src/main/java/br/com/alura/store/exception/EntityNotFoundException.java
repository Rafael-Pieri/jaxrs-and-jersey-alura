package br.com.alura.store.exception;

import br.com.alura.store.dto.ErrorMessageDTO;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundException extends RuntimeException implements ExceptionMapper<EntityNotFoundException> {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException() {}

    @Override
    public Response toResponse(EntityNotFoundException exception) {
        return Response.status(404)
            .entity(new ErrorMessageDTO(exception.getMessage()))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }
}