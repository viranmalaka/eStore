/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import org.apache.logging.log4j.Level;
import org.junit.Test;

/**
 *
 * @author Malaka
 */
public class LogControllerTest {
    
    public LogControllerTest() {
    }

    /**
     * Test of <error> method, of class LogController.
     */
    @Test
    public void logTest() {
        System.out.println("Logger Test");
        String messge = "Test log for INFO 2";
        LogController.log(Level.INFO, messge);
    }
    
}
