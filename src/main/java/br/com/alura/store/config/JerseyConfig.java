package br.com.alura.store.config;

import br.com.alura.store.resource.CartResource;
import br.com.alura.store.resource.ProjectResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages("br.com.alura.store");
        register(CartResource.class);
        register(ProjectResource.class);
    }

}