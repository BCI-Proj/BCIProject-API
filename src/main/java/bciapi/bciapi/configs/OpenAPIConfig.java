package bciapi.bciapi.configs;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.web.ErrorResponse;

/**
 * @author Raph
 * bci-api - bciapi.bciapi.configs
 * OpenAPIConfig
 * <p>
 * <p>
 * 1/27/2025
 */
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "OpenApi specification - BCI",
                version = "1.0"
        )
)
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenApiCustomizer schemaCustomizer() {
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(ErrorResponse.class));
        return openApi -> openApi
                .schema(resolvedSchema.schema.getName(), resolvedSchema.schema);
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("https://bciproject-api.onrender.com"))
                .info(new Info()
                        .title("BCI API")
                        .description("RESTFUL API for BCI Project.")
                        .version("1.0.0")
                );
    }

}
