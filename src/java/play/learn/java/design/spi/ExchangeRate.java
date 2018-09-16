package play.learn.java.design.spi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public final class ExchangeRate {
    
    private static final String DEFAULT_PROVIDER = "net.opentracker.aws.route53.YahooFinanceExchangeRateProvider";
    
    public static List<ExchangeRateProvider> providers() {
        System.out.println("ExchangeRate1");
        List<ExchangeRateProvider> services = new ArrayList<>();
        System.out.println("ExchangeRate1.1");
        try {
            ServiceLoader<ExchangeRateProvider> loader = ServiceLoader.load(ExchangeRateProvider.class);
            System.out.println("ExchangeRate1.2");
            loader.forEach(ExchangeRateProvider -> {
                System.out.println("adding " + ExchangeRateProvider);
                services.add(ExchangeRateProvider);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("ExchangeRate9");
        return services;
    }
    
    public static ExchangeRateProvider provider() {
        System.out.println("provider()");
        return provider(DEFAULT_PROVIDER);
    }
    
    public static ExchangeRateProvider provider(String providerName) {
        ServiceLoader<ExchangeRateProvider> loader = ServiceLoader.load(ExchangeRateProvider.class);
        Iterator<ExchangeRateProvider> it = loader.iterator();
        
        while (it.hasNext()) {
            ExchangeRateProvider provider = it.next();
            if (providerName.equals(provider.getClass().getName())) {
                return provider;
            }
        }
        throw new RuntimeException("Exchange Rate provider " + providerName + " not found");        
    }

}
