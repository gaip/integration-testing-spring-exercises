package spring.testing.server.integrationtests.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import spring.testing.server.configuration.ExchangeControllerConfiguration_WithMocks;
import spring.testing.server.exceptions.UnknownCurrencyException;
import spring.testing.server.exchange.Rate;
import spring.testing.server.persistence.jdbc.RateRepository;


@SpringBootTest
@ContextConfiguration(classes= {ExchangeControllerConfiguration_WithMocks.class})
@AutoConfigureMockMvc
public class ExchangeTests_WithMocks {
	
	@MockBean RateRepository mockRepository;
	@Autowired WebApplicationContext wac;
	@Autowired
	private MockMvc mockMvc;
	

	@Test
	public void getSingleRate_returnsRateDescription() throws Exception {
		Rate mockRate = new Rate("EUR", new BigDecimal(1.0));
		when(mockRepository.findByCurrency(anyString()))
			.thenReturn(mockRate);
		
		MvcResult result =  this.mockMvc
		.perform(get("/rates/currency/?name=EUR"))
    	.andExpect(status().isOk())
    	.andReturn();
		
		assertEquals("EUR = 1.000000", result.getResponse().getContentAsString());
	}


}
