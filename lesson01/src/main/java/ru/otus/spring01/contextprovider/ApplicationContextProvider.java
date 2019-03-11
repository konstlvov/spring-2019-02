/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring01.contextprovider;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author lvov_k
 * 
 * UPD. Заработало САМО ПОЧЕМУ-ТО. ЭТОТ КЛАСС ПОЧЕМУ-ТО НЕ РАБОТАЛ, и я не понимаю почему
 * UPD2. Выяснил: чтобы спринг вызвал тут setApplicationContext, надо для начала вызвать new ClassPathXmlApplicationContext прямо в main
 * То есть правильная практика в том, чтобы вызывать new ClassPathXmlApplicationContext прямо в начале функции main
 */
@Service
public class ApplicationContextProvider  implements ApplicationContextAware {
    private ApplicationContextProvider() {}
    
    private static ApplicationContext context = null;
    
    public static ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.context;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextProvider.context = applicationContext;
    }    
}
