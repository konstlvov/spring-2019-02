/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abr.springtestingapp.shell;

import com.abr.springtestingapp.ApplicationContextProvider;
import com.abr.springtestingapp.ui.Quiz;
import java.io.IOException;
import java.util.Locale;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Component;

/**
 *
 * @author lvov_k
 */
@ShellComponent
public class QuizCommands {
    @Component
    public class CustomPromptProvider implements PromptProvider {
        private String prompt = "QUIZ";
        @Override
        public AttributedString getPrompt() {
            return new AttributedString(prompt + ":>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BRIGHT | AttributedStyle.CYAN));
        }
        
        public void setPrompt(String newPrompt) {
            prompt = newPrompt;
        }

        // повод поупражняться заодно со spring events
        @EventListener
        public void handle(PromptChangeEvent event) {
            prompt = event.prompt;
        }
    }
    
    private class PromptChangeEvent {
        public String prompt;
        PromptChangeEvent(String p) {
            this.prompt = p;
        }
    }
    
    @Component
    private class PromptChangeEventProducer {
        private final ApplicationEventPublisher publisher;
        @Autowired
        PromptChangeEventProducer(ApplicationEventPublisher publisher) {
            this.publisher = publisher;
        }
        public void publishNewPrompt(String newPrompt) {
            publisher.publishEvent(new PromptChangeEvent(newPrompt));
        }
    }
    
    @Autowired
    private PromptChangeEventProducer pp;
    
    
    @ShellMethod("Runs quiz with questions.")
    public String runQuiz() throws IOException {
        System.out.println("we are ready to run quiz!");
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        Quiz q = new Quiz(ctx, new Locale(ctx.getEnvironment().getProperty("locale")));
        q.startDialogWithUser();
        pp.publishNewPrompt("QUIZ FINISHED");
        return "Quiz finished";
    }
}
