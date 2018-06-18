package com.utilities.loggerutils;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LoggerUtils 
{
	private static Logger logger = LogManager.getLogger();
	
	
	public void performSometask()
	{
		logger.debug("This is a debug message");
		logger.info("This is an info message");
		logger.warn("This is a warn message");
		logger.error("This is an error message");
		logger.fatal("This is a debug message");
	}
	

	public static void main(String[] args)
	{
		LoggerUtils logUtils = new LoggerUtils();
		logUtils.performSometask();
	}
	
}
