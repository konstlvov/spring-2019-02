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

/**
 *
 * @author lvov_k
 * 
 * UPD. Заработало САМО ПОЧЕМУ-ТО. ЭТОТ КЛАСС ПОЧЕМУ-ТО НЕ РАБОТАЛ, и я не понимаю почему
 */
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
