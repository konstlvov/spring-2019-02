package com.abr.springpatternsamples;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import static java.util.stream.Collectors.toMap;

@SpringBootApplication
public class SpringPatternSamples {

	public static void main(String[] args) {
		SpringApplication.run(SpringPatternSamples.class, args);
	}
        
  private ApplicationContext context;

	// @Autowired on single constructor exists implicitly, and because of that listOfResponders is filled by Spring
	public SpringPatternSamples(ApplicationContext context, List<Responder> listOfResponders)  {
        this.context = context;
        // this demonstrates how Spring fills map with components using result of their method getRqType as key
        this.responders = listOfResponders.stream().collect(toMap(Responder::getRqType, p -> p));
    }


    // this demonstrates how Spring puts components in map by their ID
    // IDs were set with annotations @Component("1") and @Component("2")
    @Autowired
    private Map<String, Responder> awResponders;

    private Map<String, Responder> responders;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return agrs -> {
        	// this demonstrates how to get component from map by it's ID
        	Responder r = awResponders.get("2");
            System.out.println(r.getResponse());
            // this demonstrates, how to get component by it's unique key
            r = responders.get("MsgClientAccountListRq");
            System.out.println(r.getResponse());
            // this demonstrates ablity of components to register themself in ResponderRegistry
            ResponderRegistry respReg = ctx.getBean(ResponderRegistry.class);
            r = respReg.get("MsgClientAccountListRq");
            System.out.println(r.getResponse());
        };
    }
}
