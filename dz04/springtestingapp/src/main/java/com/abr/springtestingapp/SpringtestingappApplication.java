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
        
    @Autowired
    private YAMLConfig myConfig;
    
    private ApplicationContext context;
    private Locale locale;
    public SpringtestingappApplication(ApplicationContext context) {
        this.context = context;
    }
    
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource m = new ReloadableResourceBundleMessageSource();
        m.setBasename("/i18n/bundle");
        m.setDefaultEncoding("UTF-8");
        return m;
    }
    
    public void startDialogWithUser() throws IOException {
        MessageSource ms = (MessageSource) context.getBean("messageSource");
        QuestionList ql = context.getBean(QuestionList.class); // UPD. now works! It did not work because
        // I tried to call ClassPathXmlApplicationContext one more time in bean constructor,
        // and it caused circular dependency and NoClassDefFoundError Spring exception
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String defaultName = ql.defaultUserName + " " + ql.defaultUserSurname;
        System.out.println(ms.getMessage("enter_name_prompt", null, locale) +  "(" + defaultName +"):");
        String  userName = br.readLine();
        userName = userName.isEmpty() ? defaultName : userName;
        System.out.println(userName + ", " + ms.getMessage("num_answers", null, locale) + " " + ql.getQuestionCount());
        for (Question q: ql) {
            System.out.println(ms.getMessage("question", null, locale) + ": " + q.getQuestionText());
            System.out.println(ms.getMessage("answers", null, locale) + ": " + q.getPossibleAnswersForUserDisplay());
            System.out.println(ms.getMessage("enter_your_answer", null, locale) + ":");
            String userInput = br.readLine();
            q.setUserEnteredAnswerIndex(userInput);
        }
        // your_correct_answers=number of your correct answers
        System.out.println(userName + ", " + ms.getMessage("your_correct_answers", null, locale) + ": " + ql.getNumberOfCorrectAnswers() 
                + " " + ms.getMessage("of", null, locale) + " " + ql.getQuestionCount());
        for (int i = 0; i < ql.getQuestionCount(); i++){
            Question q = ql.getQuestion(i);
            System.out.println(q.getQuestionText()
                    + " " + ms.getMessage("you_answered", null, locale) + " "
                    + (q.getRightAnswerIndex() == q.getUserEnteredAnswerIndex() ? ms.getMessage("big_correct", null, locale) : ms.getMessage("big_incorrect", null, locale)));
            System.out.println(ms.getMessage("correct_answer", null, locale) + " " + q.getRigthAnswer() + ", " + ms.getMessage("you_answered", null, locale) 
                    +" " + q.getUserEnteredAnswer());
        }
    }

    
//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName: beanNames) {
//                System.out.println(beanName);
//            }
//        };
//    }        

//    @Bean
//    public CommandLineRunner commandLineRunner2(ApplicationContext ctx) {
//        return (args) -> {
//            System.out.println("The \"name\" property in application.yml is: " + myConfig.getName());
//            // works also:
//            SpringtestingappApplication app = new SpringtestingappApplication(ctx);
//            app.setLocale(new Locale(ctx.getEnvironment().getProperty("locale")));
//            //app.startDialogWithUser();
//        };
//    }        
    

}
