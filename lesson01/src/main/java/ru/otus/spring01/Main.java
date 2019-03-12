package ru.otus.spring01;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import ru.otus.spring01.contextprovider.ApplicationContextProvider;
import ru.otus.spring01.domain.Question;
import ru.otus.spring01.domain.QuestionList;
import ru.otus.spring01.domain.QuestionListFillerClassPathCSV;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
public class Main {
    
    private ApplicationContext context;
    private Locale locale;
    

    public static String convertStreamToStringWithScanner(java.io.InputStream is) {
      java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
      return s.hasNext() ? s.next() : "";
    }
    
    public static String fastConvertStreamToString(InputStream inputStream) throws IOException{
        try(ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        }        
    }

    public Main(ApplicationContext context) {
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
        //System.out.println(ms.getMessage("hello.world", null, Locale.ROOT)); // Locale.US -> en_US, Locale.ROOT -> default locale (russian)
        //System.out.println(ms.getMessage("hello.world", null, new Locale("ru_RU"))); // this works too, "en_US" will also work
        // System.out.println(ms.getMessage("hello.world", null, locale)); just for test
        // creating QuestionList object as bean...
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
    
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        // так не работает: Spring воспринимает Main как bean и начинает ругаться, когда у конструктора два параметра
        // можно ли это как-то обойти, я не знаю
        //Main m = new Main(context, new Locale(context.getEnvironment().getProperty("locale")));
        Main m = new Main(context);
        m.setLocale(new Locale(context.getEnvironment().getProperty("locale")));
        m.startDialogWithUser();
    }
}
