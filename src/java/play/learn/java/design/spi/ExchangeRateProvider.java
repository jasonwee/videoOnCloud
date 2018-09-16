package play.learn.java.design.spi;

public interface ExchangeRateProvider {
    QuoteManager create();
}
