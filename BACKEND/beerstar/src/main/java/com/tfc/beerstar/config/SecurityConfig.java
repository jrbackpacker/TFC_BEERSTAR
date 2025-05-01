package com.tfc.beerstar.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuración de seguridad para la API REST Beerstar.
 *
 * <p>Esta clase configura Spring Security para habilitar la seguridad HTTP en la aplicación. 
 * Se desactiva CSRF, se habilita CORS y se establece una política de sesiones sin estado 
 * (sin cookies de sesión), lo cual es común en las aplicaciones RESTful. 
 * También se configura un {@link BCryptPasswordEncoder} para la encriptación de contraseñas.</p>
 *
 * <p>Es posible que desees activar autenticación en ciertas rutas, como "/beerstar/proveedores/**", 
 * dependiendo de los requisitos de tu API.</p>
 *
 * @author rafalopezzz
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura las reglas de seguridad HTTP para la aplicación.
     *
     * <p>Este método establece las siguientes configuraciones:</p>
     * <ul>
     *   <li>Habilita CORS para permitir solicitudes entre diferentes dominios.</li>
     *   <li>Deshabilita la protección CSRF, adecuada para una API sin estado.</li>
     *   <li>Permite el acceso público a las rutas de "/beerstar/**".</li>
     *   <li>Permite el acceso público a todas las demás rutas.</li>
     *   <li>Configura la política de sesiones como {@link SessionCreationPolicy#STATELESS}, 
     *       lo que significa que no se mantiene el estado de la sesión entre las solicitudes.</li>
     * </ul>
     *
     * @param http Objeto de configuración HTTP de Spring Security.
     * @return Un objeto {@link SecurityFilterChain} con las configuraciones aplicadas.
     * @throws Exception Si ocurre algún error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configurando seguridad HTTP");           
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/beerstar/**").permitAll()
                .anyRequest().permitAll()
                //.requestMatchers("/beerstar/proveedores/**").authenticated() // Para autenticar usuarios con login
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            //.cors(cors -> cors.configurationSource(corsConfigurationSource()));
            log.info("Configuración de seguridad HTTP completada");
        return http.build();
    }

    /**
     * Configura las opciones CORS para permitir solicitudes desde otros orígenes.
     *
     * <p>Este método define las reglas de CORS para la aplicación, permitiendo solicitudes 
     * desde cualquier origen (*), con los métodos HTTP GET, POST, PUT, DELETE y OPTIONS, 
     * y sin credenciales.</p>
     *
     * @return Un {@link CorsConfigurationSource} configurado con las reglas CORS.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Crea un {@link PasswordEncoder} que usa el algoritmo BCrypt para encriptar contraseñas.
     *
     * <p>Este método define cómo se deben encriptar las contraseñas en la aplicación 
     * para garantizar que las contraseñas no se almacenen en texto claro.</p>
     *
     * @return Un {@link PasswordEncoder} configurado para usar {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}