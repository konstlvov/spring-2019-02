/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring01.domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import static ru.otus.spring01.Main.fastConvertStreamToString;
import ru.otus.spring01.contextprovider.ApplicationContextProvider;

/**
 *
 * @author lvov_k
 */
public class QuestionListFillerClassPathCSV  implements IQuestionListFiller {
    
    // todo: maybe pass csv file name here...
    public QuestionListFillerClassPathCSV() {
    }
    
    @Override
    public void fillQuestionList(QuestionList ql) throws IOException {
        InputStream is = QuestionListFillerClassPathCSV.class.getResourceAsStream("/my.csv");
        String csvFileContent = fastConvertStreamToString(is);
        String[] lines = csvFileContent.split("\r?\n");
        for (String line: lines) {
            Question q = new Question();
            String[] arrAnswers = line.split(",");
            String questionText = arrAnswers.length > 0? arrAnswers[0] : "";
            q.setQuestionText(questionText);
            for (int i = 1; i < arrAnswers.length; i++) {
                String ans = arrAnswers[i];
                if (ans.charAt(0) == '_') { 
                    q.addPossibleAnswer(ans.substring(1), true); // this is the right answer
                }
                else {
                    q.addPossibleAnswer(ans, false);
                }
            }
            ql.addQuestion(q);
        }
    }
}
