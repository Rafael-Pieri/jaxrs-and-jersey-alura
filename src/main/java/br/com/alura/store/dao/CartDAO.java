package br.com.alura.store.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import br.com.alura.store.model.Cart;
import br.com.alura.store.model.Product;

public class CartDAO {

	private static Map<Long, Cart> bank = new HashMap<>();
	private static AtomicLong conter = new AtomicLong(1);

	static {
		Product videogame = new Product(6237L, "Playstation", BigDecimal.valueOf(4000), 1);
		Product sport = new Product(3467L, "Soccer Game", BigDecimal.valueOf(60), 2);
		
		Cart cart = new Cart().add(videogame)
							  .add(sport)
							  .to("Vergueiro Street 3185, 8 floor", "Sao Paulo")
							  .setId(1l);
		
		bank.put(1l, cart);
	}

	public void add(Cart cart) {
		Long id = conter.incrementAndGet();
		cart.setId(id);
		bank.put(id, cart);
	}

	public Cart find(Long id) {
		return bank.get(id);
	}

	public Cart remove(Long id) {
		return bank.remove(id);
	}

}
