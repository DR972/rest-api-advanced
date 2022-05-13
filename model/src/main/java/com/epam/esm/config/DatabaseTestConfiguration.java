package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Class {@code DatabaseTestConfiguration} contains the spring database configuration for the tests.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@Profile("test")
public class DatabaseTestConfiguration {
}
