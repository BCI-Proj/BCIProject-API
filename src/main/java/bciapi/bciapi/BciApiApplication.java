package bciapi.bciapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BciApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BciApiApplication.class, args);
        log.info("========== H2 -> http://localhost:8080/h2-console");
        log.info("========== Open API -> http://localhost:8080/swagger-ui/index.html");
    }

}
