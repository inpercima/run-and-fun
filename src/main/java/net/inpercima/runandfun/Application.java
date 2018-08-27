package net.inpercima.runandfun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Sebastian Peters
 * @author Marcel Jänicke
 * @since 30.01.2015
 */
@SpringBootApplication(scanBasePackages = { "net.inpercima" })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
