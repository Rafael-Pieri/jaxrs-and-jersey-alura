package br.com.alura.store.service;

import br.com.alura.store.repository.CartRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CartServiceTest {

    @InjectMocks
    private CartServiceTest cartService;

    @Mock
    private CartRepository cartRepository;
}
