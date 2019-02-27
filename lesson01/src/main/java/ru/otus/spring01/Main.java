package ru.otus.spring01;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import ru.otus.spring01.contextprovider.ApplicationContextProvider;
import ru.otus.spring01.domain.Question;
import ru.otus.spring01.domain.QuestionList;
import ru.otus.spring01.domain.QuestionListFillerClassPathCSV;


public class Main {
    
    private ApplicationContext context;

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
    
    public void startDialogWithUser() throws IOException {
        // creating QuestionList object as bean...
        QuestionList ql = context.getBean(QuestionList.class); // UPD. now works! It did not work because
        // I tried to call ClassPathXmlApplicationContext one more time in bean constructor,
        // and it caused circular dependency and NoClassDefFoundError Spring exception
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите Ваши имя и фамилию:");
        String userName = br.readLine();
        System.out.println(ql.getQuestionCount());
        for (Question q: ql) {
            System.out.println("Вопрос: " + q.getQuestionText());
            System.out.println("Варианты ответов: " + q.getPossibleAnswersForUserDisplay());
            System.out.println("Введите Ваш ответ:");
            String userInput = br.readLine();
            q.setUserEnteredAnswerIndex(userInput);
        }
        System.out.println(userName + ", ваше количество корректных ответов: " + ql.getNumberOfCorrectAnswers() + " из " + ql.getQuestionCount());
        for (int i = 0; i < ql.getQuestionCount(); i++){
            Question q = ql.getQuestion(i);
            System.out.println(q.getQuestionText() + " вы ответили " + (q.getRightAnswerIndex() == q.getUserEnteredAnswerIndex() ? "ВЕРНО" : "НЕВЕРНО"));
            System.out.println("Правильный ответ " + q.getRigthAnswer() + ", вы ответили " + q.getUserEnteredAnswer());
        }
    }
    
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        Main m = new Main(context);
        m.startDialogWithUser();
    }
}
