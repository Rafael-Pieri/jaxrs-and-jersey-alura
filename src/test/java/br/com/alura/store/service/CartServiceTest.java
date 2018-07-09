package br.com.alura.store.service;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.alura.store.dto.CartPostDTO;
import br.com.alura.store.dto.ProductPutDTO;
import br.com.alura.store.exception.EntityNotFoundException;
import br.com.alura.store.model.Cart;
import br.com.alura.store.model.Product;
import br.com.alura.store.repository.CartRepository;
import br.com.alura.store.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    private static final String CART_NOT_FOUND = "Cart not found";
    private static final String ONE_AVENUE = "One Avenue";
    private static final String NEW_YORK = "New York";
    private static final String SHOES = "Shoes";
    private static final String UNKNOWN_ERROR = "Unknown error";

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void shouldFindCartById() {
        final Long cartId = 1L;
        final Cart cart = this.getCart();

        when(this.cartRepository.findOne(cartId)).thenReturn(Optional.of(cart));

        this.cartService.find(cartId);

        verify(this.cartRepository, times(1)).findOne(cartId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findCartByIdShouldThrowAnException() {
        final Long cartId = 1L;

        when(this.cartRepository.findOne(cartId)).thenThrow(new EntityNotFoundException(CART_NOT_FOUND));

        this.cartService.find(cartId);
    }

    @Test
    public void findAllShouldReturnAListOfCarts() {
        final Cart cart = this.getCart();

        when(this.cartRepository.findAll()).thenReturn(Optional.of(Collections.singletonList(cart)));

        this.cartService.findAll();

        verify(this.cartRepository, times(1)).findAll();
    }

    @Test
    public void shouldSaveCart() {
        final CartPostDTO cartPostDTO = new CartPostDTO(ONE_AVENUE, NEW_YORK,
            Collections.singletonList(new Product(BigDecimal.valueOf(119.9), SHOES, 1)));

        final Cart cart = this.getCart();

        when(this.cartRepository.save(anyObject())).thenReturn(Optional.of(cart));

        this.cartService.add(cartPostDTO);

        verify(this.cartRepository, times(1)).save(anyObject());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowAnException() {
        final CartPostDTO cartPostDTO = new CartPostDTO(ONE_AVENUE, NEW_YORK,
            Collections.singletonList(new Product(BigDecimal.valueOf(119.9), SHOES, 1)));

        when(this.cartRepository.save(anyObject())).thenThrow(new IllegalArgumentException(UNKNOWN_ERROR));

        this.cartService.add(cartPostDTO);
    }

    @Test
    public void shouldDeleteCart() {
        final Long cartId = 1L;

        when(this.cartRepository.exists(cartId)).thenReturn(true);
        doNothing().when(this.cartRepository).delete(cartId);

        this.cartService.deleteCart(cartId);

        verify(this.cartRepository, times(1)).exists(cartId);
        verify(this.cartRepository, times(1)).delete(cartId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteCartShouldThrowAnException() {
        final Long cartId = 1L;

        when(this.cartRepository.exists(cartId)).thenReturn(false);

        this.cartService.deleteCart(cartId);
    }

    @Test
    public void shouldUpdateProduct() {
        final Long cartId = 1L;
        final Long productId = 1L;

        final Cart cart = this.getCart();

        when(this.cartRepository.findOne(cartId)).thenReturn(Optional.of(cart));
        when(this.productRepository.save(anyObject())).thenReturn(Optional.of(getProduct(2)));

        this.cartService.updateProduct(cartId, productId, new ProductPutDTO(2));

        verify(this.cartRepository, times(2)).findOne(cartId);
        verify(this.productRepository, times(1)).save(anyObject());
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateProductShouldThrowAnExceptionWhenCartIsNotFound() {
        final Long cartId = 1L;
        final Long productId = 1L;

        when(this.cartRepository.findOne(cartId)).thenThrow(new EntityNotFoundException(CART_NOT_FOUND));

        this.cartService.updateProduct(cartId, productId, new ProductPutDTO(2));
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateProductShouldThrowAnExceptionWhenProductIsNotFound() {
        final Long cartId = 1L;

        final Cart cart = this.getCart();

        when(this.cartRepository.findOne(cartId)).thenReturn(Optional.of(cart));
        when(this.productRepository.exists(cartId)).thenReturn(false);

        this.cartService.updateProduct(cartId, 2L, new ProductPutDTO(2));
    }

    @Test
    public void shouldRemoveProductFromTheCart() {
        final Long cartId = 1L;
        final Long productId = 1L;

        when(this.cartRepository.exists(cartId)).thenReturn(true);
        when(this.productRepository.exists(cartId)).thenReturn(true);

        doNothing().when(this.productRepository).delete(cartId);

        this.cartService.removeProduct(cartId, productId);

        verify(this.cartRepository, times(1)).exists(cartId);
        verify(this.productRepository, times(1)).exists(productId);
        verify(this.productRepository, times(1)).delete(productId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeProductShouldThrowAnExceptionWhenCartIsNotFound() {
        final Long cartId = 1L;
        final Long productId = 1L;

        when(this.cartRepository.exists(cartId)).thenReturn(false);

        this.cartService.removeProduct(cartId, productId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeProductShouldThrowAnExceptionWhenProductIsNotFound() {
        final Long cartId = 1L;
        final Long productId = 1L;

        when(this.cartRepository.exists(cartId)).thenReturn(true);
        when(this.productRepository.exists(cartId)).thenReturn(false);

        this.cartService.removeProduct(cartId, productId);
    }

    private Cart getCart() {
        final Cart cart = new Cart();
        cart.setId(1L);
        cart.setStreet(ONE_AVENUE);
        cart.setCity(NEW_YORK);
        cart.setProducts(Collections.singletonList(getProduct(1)));
        return cart;
    }

    private Product getProduct(Integer quantity) {
        final Product product = new Product();
        product.setId(1L);
        product.setName(SHOES);
        product.setPrice(BigDecimal.valueOf(119.9));
        product.setQuantity(quantity);
        return product;
    }
}