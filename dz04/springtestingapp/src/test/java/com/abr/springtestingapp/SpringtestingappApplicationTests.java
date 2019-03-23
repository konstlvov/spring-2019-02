package com.abr.springtestingapp;

import com.abr.springtestingapp.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
    // если это не написать, зависает на mvn test, видимо пытается запустить spring shell и не может
    InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class SpringtestingappApplicationTests {
    
    @Autowired
    private HelloService helloService; // spring injects instance of HelloService automatically

	@Test
	public void contextLoads() {
            assertTrue(true);
	}
        
        @Test
        public void helloMethodWorks() {
            String testUser = "TestUser";
            String r = helloService.hello(testUser);
            assertTrue(r.startsWith("Hello, "));
            assertTrue(r.endsWith(testUser));
        }
        
        @Test
        public void goodbyeMethodWorks() {
            String testUser = "TestUser";
            String r = helloService.goodbye(testUser);
            assertTrue(r.startsWith("Good bye, "));
            assertTrue(r.endsWith(testUser));
        }
        
        
        
        

}
