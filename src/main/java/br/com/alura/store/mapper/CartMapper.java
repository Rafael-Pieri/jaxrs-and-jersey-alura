package br.com.alura.store.mapper;

import br.com.alura.store.dto.CartDTO;
import br.com.alura.store.model.Cart;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CartMapper {

    public static final CartMapper INSTANCE = new CartMapper();

    private CartMapper() {

    }

    public Collection<CartDTO> toCartList(Optional<List<Cart>> carts) {
        return carts.orElseGet(ArrayList::new)
                .stream().map(cart -> {
                    CartDTO cartDTO = new CartDTO();
                    BeanUtils.copyProperties(cart, cartDTO);
                    return cartDTO;
                }).collect(Collectors.toList());
    }
}