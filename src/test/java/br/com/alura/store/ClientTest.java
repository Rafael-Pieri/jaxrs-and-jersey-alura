package br.com.alura.store;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.alura.store.Server;
import br.com.alura.store.model.Cart;
import br.com.alura.store.model.Product;
import br.com.alura.store.model.Project;

public class ClientTest {

	private HttpServer server;
	private Client client;

	@Before
	public void before() {
		server = Server.startServer();
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFeature());
		this.client = ClientBuilder.newClient(config);
	}

	@After
	public void after() {
		Server.stopServer(server);
	}

	@Test
	public void addNewCart() {
		WebTarget target = client.target("http://localhost:8080");

		Cart cart = new Cart();
		cart.add(new Product(314L, "Tablet", BigDecimal.valueOf(999), 1));
		cart.setStreet("Vergueiro Street 3185, 8 floor");
		cart.setCity("Sao Paulo");

		Entity<Cart> entity = Entity.entity(cart, MediaType.APPLICATION_JSON);
		Response response = target.path("/carts").request().post(entity);

		assertEquals(201, response.getStatus());
	}

	@Test
	public void getCartById() {
		WebTarget target = client.target("http://localhost:8080");
		Cart cart = target.path("/carts/1").request().get(Cart.class);

		assertEquals("Vergueiro Street 3185, 8 floor", cart.getStreet());
	}

	@Test
	public void getProjectById() {
		WebTarget target = client.target("http://localhost:8080");
		Project project = target.path("/projects/1").request().get(Project.class);

		assertEquals(1L, project.getId(), 0);
	}

}