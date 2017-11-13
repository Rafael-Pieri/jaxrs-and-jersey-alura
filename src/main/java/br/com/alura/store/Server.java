package br.com.alura.store;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Server {

	public static void main(String[] args) throws IOException {
		HttpServer server = startServer();
		System.in.read();
		stopServer(server);
	}

	public static void stopServer(HttpServer server) {
		server.shutdownNow();
	}

	public static HttpServer startServer() {
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.store");
		URI uri = URI.create("http://localhost:8080/");

		return GrizzlyHttpServerFactory.createHttpServer(uri, config);
	}

}
