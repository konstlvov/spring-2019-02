/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abr.springtestingapp.ui;

import com.abr.springtestingapp.YAMLConfig;
import com.abr.springtestingapp.domain.Question;
import com.abr.springtestingapp.domain.QuestionList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 *
 * @author lvov_k
 */
public class Quiz {
    
    @Autowired
    private YAMLConfig myConfig;
    
    private ApplicationContext context;
    private Locale locale;
    public Quiz(ApplicationContext context, Locale locale) {
        this.context = context;
        this.locale = locale;
    }

// не знаю почему, но если задавать бин messageSource здесь, то он создается,
// но не заполняется значениями из bundle. Поэтому оставляю создание messageSource
// в корневом классе    
//    @Bean public MessageSource messageSource() {...}


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
    
}
