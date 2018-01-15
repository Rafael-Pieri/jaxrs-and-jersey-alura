package br.com.alura.store.resource;

import br.com.alura.store.dto.CartDTO;
import br.com.alura.store.exception.EntityNotFoundException;
import br.com.alura.store.mapper.CartMapper;
import br.com.alura.store.model.Cart;
import br.com.alura.store.repository.CartRepository;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@Component
@Path("api/carts")
public class CartResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartResource.class.getName());

    @Inject
    private CartRepository cartRepository;

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CartDTO find(@PathParam("id") Long id) {
        CartDTO cartDTO = new CartDTO();

        BeanUtils.copyProperties(cartRepository.findOne(id).orElseThrow(() -> {
            LOGGER.info("The Cart " + id.toString() + " does not exist");
            return new EntityNotFoundException("Cart not found");
        }), cartDTO);

        return cartDTO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<CartDTO> findAll() {
        Optional<List<Cart>> projects = cartRepository.findAll();
        return CartMapper.INSTANCE.toCartList(projects);
    }

    //TODO is not working
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(CartDTO cartDTO) {
        Cart cart = new Cart(cartDTO);

        cartRepository.save(cart).orElseThrow(RuntimeException::new);

        URI uri = URI.create("/api/carts/" + cart.getId());

        return Response.created(uri).build();
    }

    //TODO is not working
    @Path("{id}/products/{productId}/quantity")
    @PUT
    public Response updateProduct(@PathParam("id") Long id, @PathParam("productId") Long productId, String content) {
        Cart cart = cartRepository.findOne(id).get();
//		cart.changeQuantity(cart.findProductById(productId));

        return Response.ok().build();
    }

    //TODO is not working
    @Path("{id}/products/{productId}")
    @DELETE
    public Response removeProduct(@PathParam("id") Long id, @PathParam("productId") Long productId) {
        if(!cartRepository.exists(id)){
            LOGGER.error("The Product {} does not exist.", id);
            throw new EntityNotFoundException("Product not found");
        }

        cartRepository.delete(productId);

        return Response.noContent().build();
    }

}
