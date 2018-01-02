package br.com.alura.store.resource;

import br.com.alura.store.model.Cart;
import br.com.alura.store.repository.CartRepository;
import java.net.URI;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
@Path("carts")
public class CartResource {

    @Autowired
    private CartRepository cartRepository;

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Cart find(@PathParam("id") long id) {
        return cartRepository.findOne(id).get();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Cart cart) {
        cartRepository.save(cart);

        URI uri = URI.create("/carts/" + cart.getId());

        return Response.created(uri).build();
    }

    @Path("{id}/products/{productId}")
    @DELETE
    public Response removeProduct(@PathParam("id") Long id, @PathParam("productId") Long productId) {
        cartRepository.delete(productId);

        return Response.noContent().build();
    }

    @Path("{id}/products/{productId}/quantity")
    @PUT
    public Response updateProduct(@PathParam("id") Long id, @PathParam("productId") Long productId, String content) {
        Cart cart = cartRepository.findOne(id).get();
//		cart.changeQuantity(cart.findProductById(productId));

        return Response.ok().build();
    }

}
