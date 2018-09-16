package play.learn.java.design.spi;

import javax.json.bind.JsonbBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public class YahooQuoteManagerImpl implements QuoteManager {

    static final String URL_PROVIDER =
            "https://query1.finance.yahoo.com/v7/finance/quote";
    
    public YahooQuoteManagerImpl() {
        System.out.println("init YahooQuoteManagerImpl");
    }

    @Override
    public List<Quote> getQuotes(String baseCurrency, LocalDate date) {
        System.out.println("beep");
        StringBuilder sb = new StringBuilder();
        Currency.getAvailableCurrencies().forEach(currency -> {
            /*
            if (!currency.equals(currency.getCurrencyCode())) {
                
            }
            */
            sb.append(baseCurrency).append(currency.getCurrencyCode()).append("=X").append(",");
        });

        String value = "";
        try {
            value = URLEncoder.encode(
                    sb.toString().substring(0, sb.toString().length() - 1),
                    "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String queryString = String.format("%s=%s", "symbols", value);
        String response = doGetRequest(queryString);
        System.out.println(response);
        return map(response);
    }

    private List<Quote> map(String response) {
        QuoteResponseWrapper qrw = JsonbBuilder.create().fromJson(response,
                QuoteResponseWrapper.class);
        return qrw.getQuoteResponse().getResult();
    }

    String doGetRequest(String queryString) {
        String fullUrl = URL_PROVIDER + "?" + queryString;
        System.out.println(fullUrl);

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(fullUrl);
            HttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
