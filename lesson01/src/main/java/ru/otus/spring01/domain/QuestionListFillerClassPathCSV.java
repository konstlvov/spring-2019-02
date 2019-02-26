/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring01.domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import static ru.otus.spring01.Main.fastConvertStreamToString;

/**
 *
 * @author lvov_k
 */
public class QuestionListFillerClassPathCSV  implements IQuestionListFiller {

    // todo: to library
    private String fastConvertStreamToString(InputStream inputStream) throws IOException{
        try(ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        }        
    }
    
    // todo: maybe pass csv file name here...
    public QuestionListFillerClassPathCSV() {

    }
    
    @Override
    public void fillQuestionList(QuestionList ql) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        Resource r = context.getResource("classpath:my.csv");
        InputStream is = r.getInputStream();
        String s = fastConvertStreamToString(is);
        String[] lines = s.split("\r?\n");
        for (String line: lines) {
            ql.addQuestion(new Question(line));
        }
    }
}
