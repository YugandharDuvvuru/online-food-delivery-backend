package com.cts.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("AUTHSERVICE", r -> r.path("/auth-api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://AUTHSERVICE"))

                .route("USERSERVICE", r -> r.path("/user-api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://USERSERVICE"))

                .route("OWNERSERVICE", r -> r.path("/owner-api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://OWNERSERVICE"))

                .route("MENUSERVICE", r -> r.path("/menu-api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://MENUSERVICE"))

                .route("CARTSERVICE", r -> r.path("/cart-api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://CARTSERVICE"))

                .route("ORDERSERVICE", r -> r.path("/order-api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://ORDERSERVICE"))

                .build();
    }
}
