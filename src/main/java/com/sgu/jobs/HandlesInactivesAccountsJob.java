package com.sgu.jobs;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sgu.services.UserService;

@Component
public class HandlesInactivesAccountsJob {
	public static Logger LOGGER = Logger.getLogger(HandlesInactivesAccountsJob.class);
	
	@Autowired
	private UserService userService;
	
	@Scheduled(cron = "0 0 1 * * ?")
	public void handler() {
		LOGGER.info("Iniciando verificação de contas inativas.");
		
		userService.handlesInactiveAccountsEightDaysAgo();
		userService.handlesInactiveAccountsTenDaysAgo();
	}
}
