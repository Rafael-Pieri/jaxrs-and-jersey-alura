package br.com.alura.store.mapper;

import br.com.alura.store.dto.CartDTO;
import br.com.alura.store.model.Cart;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class CartMapper {

    public static final CartMapper INSTANCE = new CartMapper();

    private CartMapper() {}

    public Collection<CartDTO> toCartList(Optional<List<Cart>> carts) {
        return carts.orElseGet(ArrayList::new)
            .stream().map(cart -> {
                CartDTO cartDTO = new CartDTO();

                BeanUtils.copyProperties(cart, cartDTO);

                cartDTO
                    .withId(cart.getId())
                    .withStreet(cart.getStreet())
                    .withCity(cart.getCity())
                    .withProducts(ProductMapper.INSTANCE.toProductsList(cart.getProducts()));

                return cartDTO;
            }).collect(Collectors.toList());
    }
}