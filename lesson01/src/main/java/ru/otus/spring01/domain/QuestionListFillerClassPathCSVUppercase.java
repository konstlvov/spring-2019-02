/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring01.domain;

import java.io.IOException;
import java.io.InputStream;
import static ru.otus.spring01.Main.fastConvertStreamToString;

/**
 *
 * @author lvov_k
 */
public class QuestionListFillerClassPathCSVUppercase implements IQuestionListFiller {
    public QuestionListFillerClassPathCSVUppercase() {
        // empty constructor
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
                    q.addPossibleAnswer(ans.substring(1).toUpperCase(), true); // this is the right answer
                }
                else {
                    q.addPossibleAnswer(ans.toUpperCase(), false);
                }
            }
            ql.addQuestion(q);
        }
    }
}
