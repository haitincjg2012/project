package com.alqsoft.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author zc
 *
 */

public class Log {
    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(Log.class); 
    @Test
    public void testLog(){
    	 logger.info("5555");
    }
    
}
