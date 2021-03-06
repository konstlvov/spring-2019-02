/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abr.springtestingapp.domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import static com.abr.springtestingapp.StaticLib.fastConvertStreamToString;
import com.abr.springtestingapp.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author lvov_k
 */
@Service
public class QuestionListFillerClassPathCSV  implements IQuestionListFiller {
    
    private String qfilename;
    public QuestionListFillerClassPathCSV(@Value("${qfilename}") String qfilename) {
        this.qfilename = qfilename;
    }
    
    @Override
    public void fillQuestionList(QuestionList ql) throws IOException {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        String loc = ctx.getEnvironment().getProperty("locale");
        InputStream is = QuestionListFillerClassPathCSV.class.getResourceAsStream("/" + qfilename + "_" + loc + ".csv");
        String csvFileContent = fastConvertStreamToString(is);
        String[] lines = csvFileContent.split("\r?\n");
        for (String line: lines) {
            Question q = new Question();
            String[] arrAnswers = line.split(",");
            String questionText = arrAnswers.length > 0? arrAnswers[0] : "";
            q.setQuestionText(questionText);
            for (int i = 1; i < arrAnswers.length; i++) {
                String ans = arrAnswers[i];
                if (ans.charAt(0) == '_') { // underscore at the beginning of the string means this is the right answer
                    q.addPossibleAnswer(ans.substring(1), true);
                }
                else {
                    q.addPossibleAnswer(ans, false);
                }
            }
            ql.addQuestion(q);
        }
    }
}
