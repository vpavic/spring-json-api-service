package io.github.vpavic.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringJsonApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJsonApiServiceApplication.class, args);
    }

}
