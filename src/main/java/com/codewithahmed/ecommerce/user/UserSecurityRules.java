package com.codewithahmed.ecommerce.user;

import com.codewithahmed.ecommerce.common.exception.SecurityRules;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

public class UserSecurityRules implements SecurityRules {

    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry.
                requestMatchers(HttpMethod.GET,"/api/v1/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/api/v1/users/{id}").hasAnyRole("ADMIN","SELLER");
    }
}
