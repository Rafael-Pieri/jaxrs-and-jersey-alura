package br.com.alura.store.mapper;

import br.com.alura.store.dto.CartDTO;
import br.com.alura.store.dto.ProductDTO;
import br.com.alura.store.dto.ProjectDTO;
import br.com.alura.store.model.Cart;
import br.com.alura.store.model.Product;
import br.com.alura.store.model.Project;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;

public class CartMapper {

    public static final CartMapper INSTANCE = new CartMapper();

    private CartMapper() {

    }

    public Collection<CartDTO> toCartList(Optional<List<Cart>> carts) {
        return carts.orElseGet(ArrayList::new)
            .stream().map(cart -> {
                List<ProductDTO> productsDTO = cart.getProducts().stream().map(product -> {
                    ProductDTO productDTO = new ProductDTO();

                    BeanUtils.copyProperties(product, productDTO);

                    productDTO
                        .withId(product.getId())
                        .withName(product.getName())
                        .withPrice(product.getPrice())
                        .withQuantity(product.getQuantity());

                    return productDTO;
                }).collect(Collectors.toList());

                CartDTO cartDTO = new CartDTO();

                BeanUtils.copyProperties(cart, cartDTO);

                cartDTO
                    .withId(cart.getId())
                    .withStreet(cart.getStreet())
                    .withCity(cart.getCity())
                    .withProducts(productsDTO);

                return cartDTO;
            }).collect(Collectors.toList());
    }
}
