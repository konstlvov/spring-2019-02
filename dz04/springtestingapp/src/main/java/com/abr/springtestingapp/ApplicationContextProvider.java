package com.abr.springtestingapp;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service // без аннотации Service не заработает
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
