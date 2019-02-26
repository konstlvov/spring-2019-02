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
        String s = fastConvertStreamToString(is);
        String[] lines = s.split("\r?\n");
        for (String line: lines) {
            ql.addQuestion(new Question(line));
        }
    }
}
