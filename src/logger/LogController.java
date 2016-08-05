/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Malaka
 */
public class LogController {
    private static Logger logger = LogManager.getLogger();
    
    /**
     * @param level
     * 1. ALL 
     * 2. Trace
     * 3. Debug
     * 4. Info
     * 5. Warn
     * 6. Error
     * 7. Fatal
     * 8. Off
     * @param msg 
     */
    
    public static void log(Level level, Object msg){
        logger.log(level, msg);
    }
}
