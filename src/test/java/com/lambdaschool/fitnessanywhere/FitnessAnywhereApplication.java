package com.lambdaschool.fitnessanywhere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main class to start the application.
 */
// @EnableJpaAuditing
@SpringBootApplication
@EnableSwagger2
public class FitnessAnywhereApplication
{
    /**
     * Main method to start the application.
     *
     * @param args Not used in this application.
     */
    public static void main(String[] args)
    {
        SpringApplication.run(FitnessAnywhereApplication.class,
            args);
    }
}
