package br.com.alura.store.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages( "br.com.alura.store.controller");
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
    }

//    @Value("${spring.jersey.application-path:/api}")
//    private String apiPath;
//
//
//    private void registerEndpoints() {
//        packages("br.com.alura.store");
//        register(CartResource.class);
//        register(ProjectResource.class);
//    }
//
//    public JerseyConfig() {
//        this.registerEndpoints();
//        BeanConfig swaggerConfig = new BeanConfig();
//        swaggerConfig.setBasePath("/api");
//        SwaggerConfigLocator.getInstance()
//            .putConfig(SwaggerContextService.CONFIG_ID_DEFAULT, swaggerConfig);
//
//        packages(getClass().getPackage().getName(),
//            ApiListingResource.class.getPackage().getName());
//    }
}