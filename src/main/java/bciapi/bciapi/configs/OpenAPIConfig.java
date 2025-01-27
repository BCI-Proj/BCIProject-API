package bciapi.bciapi.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

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

    private String serverIp = "localhost";
    private String backendPort = "8080";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("http://" + serverIp + ":" + backendPort))
                .info(new Info()
                        .title("BCI API")
                        .description("RESTFUL API for BCI Project.")
                        .version("1.0.0")
                );
    }

}
