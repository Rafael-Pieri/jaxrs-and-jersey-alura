package br.com.alura.store.resource;

import br.com.alura.store.dto.CartDTO;
import br.com.alura.store.dto.CartPostDTO;
import br.com.alura.store.dto.ProductPutDTO;
import br.com.alura.store.model.Cart;
import br.com.alura.store.service.CartService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;

@Path("api/carts")
@Component
public class CartResource {

    private CartService cartService;

    @Inject
    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(CartPostDTO cartPostDTO) {
        Cart cart = cartService.add(cartPostDTO);
        URI uri = URI.create(String.format("/api/carts/%s", cart.getId()));
        return Response.created(uri).build();
    }

    @Path("{id}/products/{productId}/quantity")
    @PUT
    public Cart updateProduct(@PathParam("id") Long id, @PathParam("productId") Long productId, ProductPutDTO productPutDTO) {
        return cartService.updateProduct(id, productId, productPutDTO);
    }

    @Path("{id}/products/{productId}")
    @DELETE
    public Response removeProduct(@PathParam("id") Long id, @PathParam("productId") Long productId) {
        cartService.removeProduct(id, productId);
        return Response.noContent().build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteCart(@PathParam("id") Long id) {
        cartService.deleteCart(id);
        return Response.noContent().build();
    }
}