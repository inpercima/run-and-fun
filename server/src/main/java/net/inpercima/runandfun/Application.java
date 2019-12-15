package net.inpercima.runandfun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Marcel Jänicke
 */
@SpringBootApplication
@ComponentScan(basePackages = "net.inpercima")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
