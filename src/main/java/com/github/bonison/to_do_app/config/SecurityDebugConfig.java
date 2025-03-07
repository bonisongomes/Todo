package com.github.bonison.to_do_app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityDebugConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityDebugConfig.class);

    @Bean
    public FilterChainProxy filterChainProxy(SecurityFilterChain securityFilterChain) {
        FilterChainProxy proxy = new FilterChainProxy(securityFilterChain);
        LOGGER.info("🔍 Security Filters in the Chain:");
        proxy.getFilterChains().forEach(chain -> {
            chain.getFilters().forEach(filter ->
                    LOGGER.info(" - " + filter.getClass().getName())
            );
        });
        return proxy;
    }
}
