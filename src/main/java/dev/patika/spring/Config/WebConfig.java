package dev.patika.spring.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("https://vet-app-frontend-fawn.vercel.app/")
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowCredentials(true);
    }
}
