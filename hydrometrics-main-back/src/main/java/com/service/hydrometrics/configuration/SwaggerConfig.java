package com.service.hydrometrics.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "hydroMetrics",
                version = "1.0.0"
        )
)
public class SwaggerConfig {

}
