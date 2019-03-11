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
public class Question {
    private String questionText;
    private ArrayList<String> answers;
    private int rightAnswerIndex;
    private int userEnteredIndex;
    
    public Question() {
        this.answers = new ArrayList<>();
        this.rightAnswerIndex = -1;
        this.userEnteredIndex = -1;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    
    public void addPossibleAnswer(String answer, boolean isCorrect) {
        answers.add(answer);
        if (isCorrect) {
            this.rightAnswerIndex = answers.size() - 1; // zero-based
        }
    }
    
    public String getQuestionText() {
        return this.questionText;
    }
    
    public int getRightAnswerIndex() {
        return rightAnswerIndex;
    }
    
    public ArrayList<String> getAnswers() {
      return answers;
    }
    
    public String getRigthAnswer() {
        return answers.get(rightAnswerIndex);
    }
    
    public String getPossibleAnswersForUserDisplay() {
        String r = "";
        for (int i = 0; i < answers.size(); i++) {
            r = r + " " + (i+1) + ". " + answers.get(i);
        }
        return r;
    }
    
    public void setUserEnteredAnswerIndex(String userInput) {
        int iUserInput = Integer.valueOf(userInput);
        this.userEnteredIndex = iUserInput - 1; // zero-based
    }
    
    public int getUserEnteredAnswerIndex() {
        return this.userEnteredIndex;
    }
    
    public String getUserEnteredAnswer(){
        String r = "";
        if (this.userEnteredIndex > -1 && this.userEnteredIndex < answers.size()) {
            r = answers.get(userEnteredIndex);
        }
        return r;
    }
    
}
