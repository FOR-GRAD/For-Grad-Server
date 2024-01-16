package umc.forgrad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ForGradApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForGradApplication.class, args);
    }
}
