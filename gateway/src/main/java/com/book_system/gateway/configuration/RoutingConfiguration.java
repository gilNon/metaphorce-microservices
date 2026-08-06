package com.book_system.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(predicateSpec -> predicateSpec.path("/book-microservice/**")
                        .filters(f -> f.rewritePath("/book-microservice/(?<segment>.*)", "/${segment}"))
                        .uri("lb://BOOK-MICROSERVICE"))
                .route(predicateSpec -> predicateSpec.path("/author-microservice/**")
                        .filters(f -> f.rewritePath("/author-microservice/(?<segment>.*)", "/${segment}"))
                        .uri("lb://AUTHOR-MICROSERVICE"))
                .build();
    }
}
