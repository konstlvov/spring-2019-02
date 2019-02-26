/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring01.domain;

import java.util.ArrayList;

/**
 *
 * @author lvov_k
 */


public class QuestionList {

    private ArrayList<Question> ql;

    public QuestionList() {
        ql = new ArrayList<>();
    }
    
    public QuestionList(IQuestionListFiller filler) {
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
        for (int i = 0; i < ql.size(); i++){
            Question q = ql.get(i);
            if (q.getRightAnswerIndex() == q.getUserEnteredAnswerIndex()) {
                r++;
            }
        }
        return r;
    }
    
    public int getNumberOfIncorrectAnswers() {
        int r = 0;
        for (int i = 0; i < ql.size(); i++){
            Question q = ql.get(i);
            if (q.getRightAnswerIndex() != q.getUserEnteredAnswerIndex()) {
                r++;
            }
        }
        return r;
    }
    
}
