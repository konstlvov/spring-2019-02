package com.abr.springtestingapp;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringtestingappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringtestingappApplication.class, args);
	}
        
    @Autowired
    private YAMLConfig myConfig;
    
    
//    private void sayHello() {
//        System.out.println("hello, context is " + ApplicationContextProvider.getApplicationContext()); // works
//    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName: beanNames) {
                System.out.println(beanName);
            }
            System.out.println("The \"name\" property in application.yml is: " + myConfig.getName());
            // works also:
            //SpringtestingappApplication app = new SpringtestingappApplication();
            //app.sayHello();
        };
    }        

}
