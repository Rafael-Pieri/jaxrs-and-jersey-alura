package br.com.alura.store.config;

import br.com.alura.store.controller.CartResource;
import br.com.alura.store.controller.ProjectResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages("br.com.alura.store");
        register(CartResource.class);
        register(ProjectResource.class);
    }
}