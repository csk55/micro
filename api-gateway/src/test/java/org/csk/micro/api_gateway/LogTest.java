package org.csk.micro.api_gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LogTest {
    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);

        public static void main(String[] args) {
            File logDir = new File("C:/csk/repo/logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
                System.out.println("Log directory created: " + logDir.getAbsolutePath());
            }

            File logFile = new File("C:/csk/repo/logs/api-gateway.log");
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                    System.out.println("Log file created: " + logFile.getAbsolutePath());
                } catch (Exception e) {
                    System.err.println("Failed to create log file: " + e.getMessage());
                }
            }

            logger.info("Testing log creation");
            logger.error("Test error message");
        }
}