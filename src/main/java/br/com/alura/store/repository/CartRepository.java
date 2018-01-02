package br.com.alura.store.repository;

import br.com.alura.store.model.Cart;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface CartRepository extends Repository<Cart, Long> {

    Optional<Cart> findOne(Long id);

    Optional<Cart> save(Cart cart);

    void delete(Long id);
}
