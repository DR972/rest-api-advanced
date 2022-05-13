package com.epam.esm.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

/**
 * Class {@code DataBaseConfiguration} contains the spring database configuration for DAO layer.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Configuration
@EntityScan(basePackages = "com.epam.esm")
public class DataBaseConfiguration {
}
