package com.hub.shop.common.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.hub.shop.publicc.service.PublicDataPersist;

/**
 * Public Scheduler Config.
 * 
 * @author Kiran
 *
 */
@Configuration
@EnableScheduling
public class PublicScheduerConfig {

	@Bean
	public PublicDataPersist bean() {
		//return new PublicDataPersist();
		return null;
	}
}
