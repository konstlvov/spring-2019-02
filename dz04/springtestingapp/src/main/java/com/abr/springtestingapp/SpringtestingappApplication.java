package com.abr.springtestingapp;

import com.abr.springtestingapp.domain.QuestionList;
import com.abr.springtestingapp.domain.Question;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class SpringtestingappApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringtestingappApplication.class, args);
    }
        
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource m = new ReloadableResourceBundleMessageSource();
        m.setBasename("/i18n/bundle");
        m.setDefaultEncoding("UTF-8");
        return m;
    }
    

}
