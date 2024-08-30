package com.collection_web_application.Configurations;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import java.util.Locale;

// Marks this class as a configuration class for Spring, providing bean definitions.
@Configuration
public class languageConfig implements WebMvcConfigurer {

    // Defines a bean for resolving the locale (language) for each user session.
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US); // Set the default locale to US English.
        return slr;
    }

    // Defines a bean for intercepting requests to change the locale based on a parameter.
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); // Set the request parameter name that will be used to change the locale.
        return lci;
    }

    // Defines a bean for loading message properties files for internationalization (i18n).
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8"); // Ensure UTF-8 encoding for the message files.
        return messageSource;
    }

    // Registers the locale change interceptor to handle requests for changing the locale.
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}


/*
Example message properties files for internationalization (i18n):

messages.properties (English)
navbar.home=Home
..
messages_bn.properties (Bengali)
navbar.home=বাড়ি
*/
