package br.com.alura.store.repository;

import br.com.alura.store.model.Product;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface ProductRepository extends Repository<Product, Long> {

    Optional<Product> save(Product product);

    void delete(Long id);

    boolean exists(Long id);
}