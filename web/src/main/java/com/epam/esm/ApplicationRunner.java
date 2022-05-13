package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.Locale;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class ApplicationRunner {

    /**
     * The entry point of Spring Boot application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }

    /**
     * This method creates a bean that will be used to get LocaleResolver.
     *
     * @return the message source
     */
    @Bean
    public LocaleResolver localeResolver() {
        final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setSupportedLocales(Arrays.asList(new Locale("ru"), new Locale("en")));
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }

    /**
     * This method creates a bean that will be used to get info from properties files.
     *
     * @return the message source
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("localization/message");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        return resourceBundleMessageSource;
    }
}
