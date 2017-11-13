package br.com.alura.store.resource;

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

import br.com.alura.store.dao.CartDAO;
import br.com.alura.store.model.Cart;

@Path("carts")
public class CartResource {

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Cart find(@PathParam("id") long id) {
		return new CartDAO().find(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Cart cart) {
		new CartDAO().add(cart);

		URI uri = URI.create("/carts/" + cart.getId());

		return Response.created(uri).build();
	}

	@Path("{id}/products/{productId}")
	@DELETE
	public Response removeProduct(@PathParam("id") Long id, @PathParam("productId") Long productId) {
		new CartDAO().find(id).remove(productId);

		return Response.noContent().build();
	}

	@Path("{id}/products/{productId}/quantity")
	@PUT
	public Response updateProduct(@PathParam("id") Long id, @PathParam("productId") Long productId, String content) {
		Cart cart = new CartDAO().find(id);
		cart.changeQuantity(cart.findProductById(productId));

		return Response.ok().build();
	}

}
