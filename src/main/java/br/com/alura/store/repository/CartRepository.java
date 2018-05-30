package br.com.alura.store.repository;

import br.com.alura.store.model.Cart;
import br.com.alura.store.model.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface CartRepository extends Repository<Cart, Long> {

    Optional<Cart> findOne(Long id);

    Optional<List<Cart>> findAll();

    Optional<Cart> save(Cart cart);

    void delete(Long id);

    boolean exists(Long id);
}