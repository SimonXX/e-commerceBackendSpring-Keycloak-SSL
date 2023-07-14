package com.shop.ecommerce.config;

import com.shop.ecommerce.support.authentication.JwtAuthConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity//per aggiungere i ruoli
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()//disabilita protezione csrf
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated();//configura le regole di autorizzazione, viene richiesto che tutte le richieste siano autenticate

        http
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthConverter);;//Configura il server delle risorse OAuth2 per supportare l'autenticazione basata su token JWT

        // personalizzare il processo di conversione del token JWT in oggetto di autenticazione.

        http
                .sessionManagement()
                .sessionCreationPolicy(STATELESS);//: Configura la gestione delle sessioni e imposta la politica di creazione delle sessioni come STATELESS. Ciò significa
        // che non verranno create sessioni per le richieste in arrivo e ogni richiesta verrà gestita in modo indipendente dalle altre.

        return http.build();//: Restituisce la catena di filtri di sicurezza configurata.
    }
}