/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring05.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Component;

@ShellComponent

public class BookLibCommands {
    
    @Component
    public class CustomPromptProvider implements PromptProvider {
        private String prompt = "QUIZ";
        @Override
        public AttributedString getPrompt() {
            return new AttributedString(prompt + ":>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
        }
        
        public void setPrompt(String newPrompt) {
            prompt = newPrompt;
        }

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

  
  // если менять аргументы местами, то именовать параметры через дефис, а не в camelCase:
  // add-book --book-title "The catcher in the rye" --book-author "Jerome Salinger"
  // также можно написать help add-book, и оно выведет описание и имена параметров
  @ShellMethod("Adds book to library")
  public String addBook(@ShellOption String bookAuthor, @ShellOption String bookTitle) {
      return "I'm going to add book \"" + bookTitle + "\" written by " + bookAuthor + " to library";
  }
  
  @ShellMethod("Runs interactive quiz session with user")
  public String runQuiz() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your name:>");
        pp.publishNewPrompt("QUIZ FINISHED"); // wow, also works
        String  userName = br.readLine();
        return "Your name is " + userName;
  }
  

}

