package br.com.alura.store.mapper;

import br.com.alura.store.dto.ProductDTO;
import br.com.alura.store.model.Product;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class ProductMapper {

    public static final ProductMapper INSTANCE = new ProductMapper();

    private ProductMapper() {}

    public Collection<ProductDTO> toProductsList(Collection<Product> products) {
        return products.stream().map(product -> {
            ProductDTO productDTO = new ProductDTO();

            BeanUtils.copyProperties(product, productDTO);

            productDTO
                .withId(product.getId())
                .withName(product.getName())
                .withPrice(product.getPrice())
                .withQuantity(product.getQuantity());

            return productDTO;
        }).collect(Collectors.toList());
    }
}