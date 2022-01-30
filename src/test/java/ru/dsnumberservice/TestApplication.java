package ru.dsnumberservice;

import org.springframework.boot.SpringApplication;
import ru.dsnumberservice.configuration.DbInitializer;

public class TestApplication {

    public static void main(String[] args) {
        SpringApplication application = Application.createSpringApplication();
        application.addInitializers(new DbInitializer(5432));
        application.run(args);
    }
}
