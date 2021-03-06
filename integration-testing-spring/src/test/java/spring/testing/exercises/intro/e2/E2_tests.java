package spring.testing.exercises.intro.e2;

import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import spring.testing.server.exchange.Exchange;
import spring.testing.server.exchange.RateParser;
@SpringBootTest
@ContextConfiguration(classes= {E2_configuration.class })
public class E2_tests {

	@Autowired RateParser parser;
	@MockBean Exchange mockExchange;
	
	final String templateWithSpaces = "^(...)\\s=\\s(\\d+\\.?\\d*)$";
	final String templateEndingWithComma ="^(...)=(\\d+\\.?\\d*)\\,$";
	final String templateEndingWithSemicolon ="^(...)=(\\d+\\.?\\d*)\\;$";
	
	List<String> rates;
	
	@BeforeEach
	public void setup( ) {
		rates = new ArrayList<String>();
		Mockito.reset(mockExchange);
	}
	
	@Test
	public void rateWithnoSpaces_areParsedCorrectly() {
		rates.add("ILS=2.5");
		assertExchangeWasCalledWith("ILS", 2.5);
	}

	@Test
	public void rateWithSpaces_areParsedCorrectly() {
		parser.addLineTemplate(templateWithSpaces);
		rates.add("ILS = 2.5");
		assertExchangeWasCalledWith("ILS", 2.5);
	}
	
	@Test
	public void rateEndingWithComma_areParsedCorrectly() {
		parser.addLineTemplate(templateEndingWithComma);
		rates.add("ILS=2.5,");
		assertExchangeWasCalledWith("ILS", 2.5);
	}
	
	@Test
	public void rateEndingWithSemicolon_areParsedCorrectly() {
		parser.addLineTemplate(templateEndingWithSemicolon);
		rates.add("ILS=2.5;");
		assertExchangeWasCalledWith("ILS", 2.5);
	}

	private void assertExchangeWasCalledWith(String currency, double value) {
		parser.parse(rates);
		verify(mockExchange).setRate(currency, BigDecimal.valueOf(value));
	}
	
	
}
