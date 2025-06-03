package APSV.Controller.Validacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Libera todos os endpoints
                        .allowedOrigins("*") // Permite requisições de qualquer origem
                        .allowedMethods("*") // Permite qualquer método (GET, POST, PUT, DELETE, etc.)
                        .allowedHeaders("*") // Permite qualquer header
                        .allowCredentials(false); // Sem cookies/sessões compartilhadas
            }
        };
    }
}

