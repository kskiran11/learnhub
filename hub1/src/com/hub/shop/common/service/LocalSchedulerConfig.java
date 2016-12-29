package com.hub.shop.common.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.hub.shop.local.service.LocalDataPoller;

/**
 * Scheduler Class
 * 
 * @author Kiran
 *
 */
@Configuration
@EnableScheduling
public class LocalSchedulerConfig {

	@Bean
	public LocalDataPoller bean() {
		return new LocalDataPoller();
	}
}