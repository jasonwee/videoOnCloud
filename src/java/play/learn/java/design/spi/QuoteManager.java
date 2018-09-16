package play.learn.java.design.spi;

import java.time.LocalDate;
import java.util.List;

public interface QuoteManager {
    List<Quote> getQuotes(String baseCurrency, LocalDate date);

}
