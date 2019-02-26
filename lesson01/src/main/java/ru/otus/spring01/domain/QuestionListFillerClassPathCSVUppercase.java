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
        String s = fastConvertStreamToString(is);
        String[] lines = s.split("\r?\n");
        for (String line: lines) {
            ql.addQuestion(new Question(line.toUpperCase()));
        }
    }
}
