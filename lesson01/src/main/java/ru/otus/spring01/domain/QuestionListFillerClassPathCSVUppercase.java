/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring01.domain;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.stereotype.Service;
import static ru.otus.spring01.Main.fastConvertStreamToString;

/**
 *
 * @author lvov_k
 */
// Поскольку хотим использовать QuestionListFillerClassPathCSV, а не QuestionListFillerClassPathCSVUppercase,
// то аннотацию @Service на этом классе комментируем. Если оставить открытыми аннотацию @Service и на QuestionListFillerClassPathCSV, и на 
// QuestionListFillerClassPathCSVUppercase (то есть одновременно на двух реализациях интерфейса IQuestionListFiller),
// то спринг выдаст ошибку:
// WARNING: Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'questionList' defined in file [C:\Users\lvov_k\spring\otus\spring-2019-02\lesson01\target\classes\ru\otus\spring01\domain\QuestionList.class]: Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'ru.otus.spring01.domain.IQuestionListFiller' available: expected single matching bean but found 2: questionListFillerClassPathCSV,questionListFillerClassPathCSVUppercase
// Exception in thread "main" org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'questionList' defined in file [C:\Users\lvov_k\spring\otus\spring-2019-02\lesson01\target\classes\ru\otus\spring01\domain\QuestionList.class]: Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'ru.otus.spring01.domain.IQuestionListFiller' available: expected single matching bean but found 2: questionListFillerClassPathCSV,questionListFillerClassPathCSVUppercase
//
// Соответственно, если надо переключиться на использование реализации QuestionListFillerClassPathCSVUppercase, то здесь аннотацию @Service раскомментируем,
// а в QuestionListFillerClassPathCSV - наоборот комментируем.

//@Service
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
