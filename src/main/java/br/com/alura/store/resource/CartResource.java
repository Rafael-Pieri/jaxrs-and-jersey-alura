package br.com.alura.store.resource;

import br.com.alura.store.dto.CartDTO;
import br.com.alura.store.model.Cart;
import br.com.alura.store.service.CartService;
import com.holonplatform.jaxrs.swagger.annotations.ApiDefinition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.Collection;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@ApiDefinition(docsPath = "/api/docs", title = "Example API", version = "v1", prettyPrint = true)
@Api("Test API")
@Component
@Path("api/carts")
public class CartResource {

    @Inject
    private CartService cartService;

    @ApiOperation("Ping request")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK: pong", response = String.class) })
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CartDTO find(@PathParam("id") Long id) {
        return cartService.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<CartDTO> findAll() {
        return cartService.findAll();
    }

    //TODO is not working
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Response add(CartDTO cartDTO) {
        Cart cart = cartService.add(cartDTO);
        URI uri = URI.create("/api/carts/" + cart.getId());
        return Response.created(uri).build();
    }

    //TODO is not working
    @Path("{id}/products/{productId}/quantity")
    @PUT
    public Response updateProduct(@PathParam("id") Long id, @PathParam("productId") Long productId, String content) {
        Cart cart = cartService.updateProduct(id, productId, content);

//		cart.changeQuantity(cart.findProductById(productId));

        return Response.ok().build();
    }

    //TODO is not working
    @Path("{id}/products/{productId}")
    @DELETE
    public Response removeProduct(@PathParam("id") Long id, @PathParam("productId") Long productId) {
        cartService.removeProduct(id, productId);
        return Response.noContent().build();
    }
}