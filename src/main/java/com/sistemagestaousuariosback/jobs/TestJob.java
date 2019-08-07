package com.sistemagestaousuariosback.jobs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestJob {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
//    @Scheduled(fixedRate = 2000)
    public void scheduleTaskWithFixedRate() {
        System.out.println("Fixed Rate Task :: Execution Time - " + dateTimeFormatter.format(LocalDateTime.now()));
    }

//    @Scheduled(fixedDelay = 2000)
    public void scheduleTaskWithFixedDelay() {
    	System.out.println("Fixed Delay Task :: Execution Time - " + dateTimeFormatter.format(LocalDateTime.now()));
        
    	try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
        	System.out.println("Ran into an error " + ex);
            throw new IllegalStateException(ex);
        }
    }

//    @Scheduled(fixedRate = 2000, initialDelay = 5000)
    public void scheduleTaskWithInitialDelay() {
    	System.out.println("Fixed Rate Task with Initial Delay :: Execution Time - " + dateTimeFormatter.format(LocalDateTime.now()));
    }

//    @Scheduled(cron = "0 * * * * ?")
    public void scheduleTaskWithCronExpression() {
    	System.out.println("Cron Task :: Execution Time - " + dateTimeFormatter.format(LocalDateTime.now()));
    }
}
