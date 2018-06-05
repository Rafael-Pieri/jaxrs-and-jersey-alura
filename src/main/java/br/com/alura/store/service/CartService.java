package br.com.alura.store.service;

import br.com.alura.store.dto.CartDTO;
import br.com.alura.store.exception.EntityNotFoundException;
import br.com.alura.store.mapper.CartMapper;
import br.com.alura.store.model.Cart;
import br.com.alura.store.repository.CartRepository;
import br.com.alura.store.repository.ProductRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class.getName());

    @Inject
    private CartRepository cartRepository;

    @Inject
    private ProductRepository productRepository;

    public CartDTO find(Long id) {
        CartDTO cartDTO = new CartDTO();

        BeanUtils.copyProperties(cartRepository.findOne(id).orElseThrow(() -> {
            LOGGER.info("The Cart {} does not exist", id);
            return new EntityNotFoundException("Cart not found");
        }), cartDTO);

        return cartDTO;
    }

    public Collection<CartDTO> findAll() {
        Optional<List<Cart>> projects = cartRepository.findAll();
        return CartMapper.INSTANCE.toCartList(projects);
    }

    public Cart add(CartDTO cartDTO) {
        return cartRepository.save(new Cart(cartDTO)).orElseThrow(RuntimeException::new);
        //        productRepository.save(cart.getProducts());
    }

    public Cart updateProduct(Long id, Long productId, String content) {
        Cart cart = cartRepository.findOne(id).get();
//		cart.changeQuantity(cart.findProductById(productId));
        return cart;
    }

    public void removeProduct(Long id, Long productId) {
        if(!cartRepository.exists(id)){
            LOGGER.error("The Product {} does not exist.", id);
            throw new EntityNotFoundException("Product not found");
        }

        cartRepository.delete(productId);
    }
}