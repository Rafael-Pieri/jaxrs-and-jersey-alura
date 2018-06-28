package br.com.alura.store.service;

import br.com.alura.store.dto.CartDTO;
import br.com.alura.store.dto.CartPostDTO;
import br.com.alura.store.dto.ProductPutDTO;
import br.com.alura.store.exception.EntityNotFoundException;
import br.com.alura.store.mapper.CartMapper;
import br.com.alura.store.model.Cart;
import br.com.alura.store.model.Product;
import br.com.alura.store.repository.CartRepository;
import br.com.alura.store.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class.getName());

    private static final String THE_CART_DOES_NOT_EXIST = "The Cart {} does not exist";
    private static final String CART_NOT_FOUND = "Cart not found";
    private static final String THE_PRODUCT_DOES_NOT_EXIST = "The Product {} does not exist";
    private static final String PRODUCT_NOT_FOUND = "Product not found";

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Inject
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public CartDTO find(Long id) {
        CartDTO cartDTO = new CartDTO();
        BeanUtils.copyProperties(getCartById(id), cartDTO);
        return cartDTO;
    }

    public Collection<CartDTO> findAll() {
        Optional<List<Cart>> projects = cartRepository.findAll();
        return CartMapper.INSTANCE.toCartList(projects);
    }

    @Transactional
    public Cart add(CartPostDTO cartPostDTO) {

//    public Cart(CartPostDTO cartPostDTO) {
//            BeanUtils.copyProperties(cartPostDTO, this);
//            this.street = cartPostDTO.getStreet();
//            this.city = cartPostDTO.getCity();
//            this.products = cartPostDTO.getProducts().stream()
//                    .map(Product::new)
//                    .collect(Collectors.toCollection(ArrayList::new));
//        }
        Cart cart1 = new Cart();
        BeanUtils.copyProperties(cartPostDTO, cart1);
        Cart cart = cartRepository.save(cart1).orElseThrow(RuntimeException::new);

//        cartPostDTO.getProducts().forEach(productDTO -> {
//            productDTO.setId(cart.getId());
//            productRepository.save(new Product(productDTO));
//        });

        return cartRepository.findOne(cart.getId()).orElseThrow(RuntimeException::new);
    }

    public Cart updateProduct(Long id, Long productId, ProductPutDTO quantity) {
        Optional<Product> productSelected = getCartById(id)
                .getProducts()
                .stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();

        if (!productSelected.isPresent()) {
            LOGGER.error(THE_PRODUCT_DOES_NOT_EXIST, id);
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND);
        }

        Product product = productSelected.get();
        product.setQuantity(quantity.getQuantity());
        productRepository.save(product);

        return getCartById(id);
    }

    public void removeProduct(Long id, Long productId) {
        checkIfCartExists(id);

        if (!productRepository.exists(id)) {
            LOGGER.error(THE_PRODUCT_DOES_NOT_EXIST, id);
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND);
        }

        productRepository.delete(productId);
    }

    public void deleteCart(Long id) {
        checkIfCartExists(id);
        cartRepository.delete(id);
    }

    private Cart getCartById(Long id) {
        return cartRepository.findOne(id).orElseThrow(() -> {
            LOGGER.info(THE_CART_DOES_NOT_EXIST, id);
            return new EntityNotFoundException(CART_NOT_FOUND);
        });
    }

    private void checkIfCartExists(Long id) {
        if (!cartRepository.exists(id)) {
            LOGGER.info(THE_CART_DOES_NOT_EXIST, id);
            throw new EntityNotFoundException(CART_NOT_FOUND);
        }
    }
}