package br.com.alura.store.controller;

import br.com.alura.store.dto.*;
import br.com.alura.store.model.Cart;
import br.com.alura.store.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CartIT {

    private static final String API_PATH = "/api/carts";
    private static final String LOCATION = "Location";
    private static final String ONE_AVENUE = "One avenue";
    private static final String NEW_YORK = "New york";
    private static final String SHOES = "Shoes";
    private static final String CART_NOT_FOUND = "Cart not found";
    private static final String PRODUCT_NOT_FOUND = "Product not found";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void findShouldReturnOk() {
        final List<Product> products = Collections.singletonList(new Product(BigDecimal.valueOf(115), SHOES, 1));

        final CartPostDTO cartPostDTO = new CartPostDTO(ONE_AVENUE, NEW_YORK, products);

        final ResponseEntity<Cart> cartCreated = this.restTemplate
                .postForEntity(API_PATH, cartPostDTO, Cart.class);

        final String location = getLocation(cartCreated);

        final ResponseEntity<CartDTO> cartFound = this.restTemplate
                .getForEntity(location, CartDTO.class);

        assertThat(cartFound.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(cartFound.getBody().getId()).isNotNull();
        assertThat(cartFound.getBody().getStreet()).isEqualTo(ONE_AVENUE);
        assertThat(cartFound.getBody().getCity()).isEqualTo(NEW_YORK);
        assertThat(cartFound.getBody().getProducts()).hasSize(1);

        this.restTemplate.delete(location);
    }

    @Test
    public void findShouldReturnNotFound() {
        final ResponseEntity<ErrorMessageDTO> cartFound = this.restTemplate
                .getForEntity("/api/carts/123", ErrorMessageDTO.class);

        assertThat(cartFound.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(cartFound.getBody().getErrorMessage()).isEqualTo(CART_NOT_FOUND);
    }

    @Test
    public void findAllShouldReturnOk() {
        final List<Product> productsOfCartOne = Collections.singletonList(new Product(BigDecimal.valueOf(115), SHOES, 1));
        final CartPostDTO cartOneDTO = new CartPostDTO(ONE_AVENUE, NEW_YORK, productsOfCartOne);
        final ResponseEntity<Cart> cartOneCreated = this.restTemplate
                .postForEntity(API_PATH, cartOneDTO, Cart.class);

        final List<Product> productsOfCartTwo = Collections.singletonList(new Product(BigDecimal.valueOf(115), SHOES, 1));
        final CartPostDTO cartTwoDTO = new CartPostDTO(ONE_AVENUE, NEW_YORK, productsOfCartTwo);
        final ResponseEntity<Cart> cartTwoCreated = this.restTemplate
                .postForEntity(API_PATH, cartTwoDTO, Cart.class);

        final ResponseEntity<ProjectDTO[]> cartsFound = this.restTemplate
                .getForEntity(API_PATH, ProjectDTO[].class);

        assertThat(cartsFound.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(cartsFound.getBody()).hasSize(2);

        this.restTemplate.delete(getLocation(cartOneCreated));
        this.restTemplate.delete(getLocation(cartTwoCreated));
    }

    @Test
    public void createShouldReturnCreated() {
        final List<Product> products = Collections.singletonList(new Product(BigDecimal.valueOf(115), SHOES, 1));

        final CartPostDTO cartPostDTO = new CartPostDTO(ONE_AVENUE, NEW_YORK, products);

        final ResponseEntity<Cart> cartCreated = this.restTemplate
                .postForEntity(API_PATH, cartPostDTO, Cart.class);

        assertThat(cartCreated.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(cartCreated.getHeaders()).containsKey(LOCATION);
        assertThat(cartCreated.getBody()).isNull();

        this.restTemplate.delete(getLocation(cartCreated));
    }

    @Test
    public void updateProjectShouldReturnNoContent() {
        final List<Product> productsDTO = Collections.singletonList(new Product(BigDecimal.valueOf(115), SHOES, 1));

        final CartPostDTO cartPostDTO = new CartPostDTO(ONE_AVENUE, NEW_YORK, productsDTO);

        final ResponseEntity<Cart> cartCreated = this.restTemplate
                .postForEntity(API_PATH, cartPostDTO, Cart.class);

        final String location = getLocation(cartCreated);

        final ResponseEntity<CartDTO> cartFound = this.restTemplate
                .getForEntity(location, CartDTO.class);

        final Product product = cartFound.getBody().getProducts().stream().findFirst().orElseThrow(RuntimeException::new);

        assertThat(product.getQuantity()).isEqualTo(1);

        final ProductPutDTO productPutDTO = new ProductPutDTO(2);

        final HttpEntity<ProductPutDTO> requestEntity = new HttpEntity<>(productPutDTO);

        final ResponseEntity<Cart> cartUpdated = this.restTemplate.exchange(String.format("/api/carts/%s/products/%s/quantity",
                cartFound.getBody().getId(), product.getId()), HttpMethod.PUT, requestEntity, Cart.class);

        final Product productUpdated = cartUpdated.getBody().getProducts().stream().findFirst().orElseThrow(RuntimeException::new);

        assertThat(cartUpdated.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productUpdated.getQuantity()).isEqualTo(2);

        this.restTemplate.delete(getLocation(cartCreated));
    }

    @Test
    public void updateProjectShouldReturnProductNotFound() {
        final List<Product> products = Collections.singletonList(new Product(BigDecimal.valueOf(115), SHOES, 1));

        final CartPostDTO cartPostDTO = new CartPostDTO(ONE_AVENUE, NEW_YORK, products);

        final ResponseEntity<Cart> cartCreated = this.restTemplate
                .postForEntity(API_PATH, cartPostDTO, Cart.class);

        final String location = getLocation(cartCreated);

        final ResponseEntity<CartDTO> cartFound = this.restTemplate
                .getForEntity(location, CartDTO.class);

        final ProductPutDTO productPutDTO = new ProductPutDTO(2);

        final HttpEntity<ProductPutDTO> requestEntity = new HttpEntity<>(productPutDTO);

        final ResponseEntity<ErrorMessageDTO> response = this.restTemplate.exchange(String.format("/api/carts/%s/products/123/quantity",
                cartFound.getBody().getId()), HttpMethod.PUT, requestEntity, ErrorMessageDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getErrorMessage()).isEqualTo(PRODUCT_NOT_FOUND);

        this.restTemplate.delete(getLocation(cartCreated));
    }

    @Test
    public void updateProjectShouldReturnCartNotFound() {
        final ProductPutDTO productPutDTO = new ProductPutDTO(1);

        final HttpEntity<ProductPutDTO> requestEntity = new HttpEntity<>(productPutDTO);

        final ResponseEntity<ErrorMessageDTO> response = this.restTemplate
                .exchange("/api/carts/123/products/1/quantity", HttpMethod.PUT, requestEntity, ErrorMessageDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getErrorMessage()).isEqualTo(CART_NOT_FOUND);
    }

    @Test
    public void removeProjectShouldReturnNoContent() {
        final List<Product> productsDTO = Collections.singletonList(new Product(BigDecimal.valueOf(115), SHOES, 1));

        final CartPostDTO cartPostDTO = new CartPostDTO(ONE_AVENUE, NEW_YORK, productsDTO);

        final ResponseEntity<Cart> cartCreated = this.restTemplate
                .postForEntity(API_PATH, cartPostDTO, Cart.class);

        final String location = getLocation(cartCreated);

        final ResponseEntity<CartDTO> cartFound = this.restTemplate
                .getForEntity(location, CartDTO.class);

        final Product products = cartFound.getBody().getProducts().stream().findFirst().orElseThrow(RuntimeException::new);

        final ResponseEntity<Void> response = this.restTemplate.exchange(String.format("/api/carts/%s/products/%s",
                cartFound.getBody().getId(), products.getId()), HttpMethod.DELETE, RequestEntity.EMPTY, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        this.restTemplate.delete(getLocation(cartCreated));
    }

    @Test
    public void removeProjectShouldReturnProductNotFound() {
        final List<Product> products = Collections.singletonList(new Product(BigDecimal.valueOf(115), SHOES, 1));

        final CartPostDTO cartPostDTO = new CartPostDTO(ONE_AVENUE, NEW_YORK, products);

        final ResponseEntity<Cart> cartCreated = this.restTemplate
                .postForEntity(API_PATH, cartPostDTO, Cart.class);

        final String location = getLocation(cartCreated);

        final ResponseEntity<CartDTO> cartFound = this.restTemplate
                .getForEntity(location, CartDTO.class);

        final ResponseEntity<ErrorMessageDTO> response = this.restTemplate.exchange(String.format("/api/carts/%s/products/123",
                cartFound.getBody().getId()), HttpMethod.DELETE, RequestEntity.EMPTY, ErrorMessageDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getErrorMessage()).isEqualTo(PRODUCT_NOT_FOUND);

        this.restTemplate.delete(getLocation(cartCreated));
    }

    @Test
    public void removeProjectShouldReturnCartNotFound() {
        final ResponseEntity<ErrorMessageDTO> response = this.restTemplate
                .exchange("/api/carts/123/products/1", HttpMethod.DELETE, RequestEntity.EMPTY, ErrorMessageDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getErrorMessage()).isEqualTo(CART_NOT_FOUND);
    }

    @Test
    public void deleteShouldReturnNoContent() {
        final List<Product> products = Collections.singletonList(new Product(BigDecimal.valueOf(115), SHOES, 1));

        final CartPostDTO cartPostDTO = new CartPostDTO(ONE_AVENUE, NEW_YORK, products);

        final ResponseEntity<Cart> cartCreated = this.restTemplate
                .postForEntity(API_PATH, cartPostDTO, Cart.class);

        final String location = getLocation(cartCreated);

        final ResponseEntity<Void> response = this.restTemplate
                .exchange(location, HttpMethod.DELETE, RequestEntity.EMPTY, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void deleteShouldReturnNotFound() {
        final ResponseEntity<ErrorMessageDTO> response = this.restTemplate
                .exchange("/api/carts/123", HttpMethod.DELETE, RequestEntity.EMPTY, ErrorMessageDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getErrorMessage()).isEqualTo(CART_NOT_FOUND);
    }

    private String getLocation(ResponseEntity<Cart> responseEntity) {
        return responseEntity.getHeaders().get(LOCATION).get(0);
    }
}