package atd.spring.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import atd.spring.server.gateway.CheeseExchangeController;
import atd.spring.server.gateway.StatusMonitor;
import atd.spring.server.helpers.FaultyMonitor;

@Profile("hacker")
@Configuration
public class HackerConfiguration {

	@Primary
	@Bean
	public StatusMonitor faultyMonitor() {
		System.out.println("Got a faulty one here");
		return new FaultyMonitor();
	}
	
	@Bean
	public CheeseExchangeController exchangeController() {
		return new CheeseExchangeController();
	}
	

}