/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring01.domain;

import java.io.IOException;

/**
 *
 * @author lvov_k
 */
public interface IQuestionListFiller {
    public void fillQuestionList(QuestionList ql) throws IOException;
}
