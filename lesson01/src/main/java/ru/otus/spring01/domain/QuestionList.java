/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring01.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author lvov_k
 */

@Service
public class QuestionList implements Iterable<Question> {
    @Value("${name}") public String defaultUserName;
    @Value("${surname}") public String defaultUserSurname;

    private ArrayList<Question> ql;

    //public QuestionList() {
    //    ql = new ArrayList<>();
    //}
    
    // IQuestionListFiller - correct case
    // QuestionListFillerClassPathCSV - testing debug case
    public QuestionList(IQuestionListFiller filler) throws IOException {
        ql = new ArrayList<>();
        filler.fillQuestionList(this);
    }

    
    public void addQuestion(Question q) {
        ql.add(q);
    }
    
    public int getQuestionCount() {
        return ql.size();
    }
    
    public Question getQuestion(int index) {
        return ql.get(index);
    }
    
    public int getNumberOfCorrectAnswers() {
        int r = 0;
        for (Question q: this.ql){
            if (q.getRightAnswerIndex() == q.getUserEnteredAnswerIndex()) {
                r++;
            }
        }
        return r;
    }
    
    public int getNumberOfIncorrectAnswers() {
        int r = 0;
        for (Question q: this.ql){
            if (q.getRightAnswerIndex() != q.getUserEnteredAnswerIndex()) {
                r++;
            }
        }
        return r;
    }

    @Override
    public Iterator<Question> iterator() {
        return ql.iterator();
    }
    
}
