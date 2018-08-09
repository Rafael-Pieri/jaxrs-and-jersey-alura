package br.com.alura.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class JaxrsAndJerseyAluraApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(JaxrsAndJerseyAluraApplication.class, args);
    }
}