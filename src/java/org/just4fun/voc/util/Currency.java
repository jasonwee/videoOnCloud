package org.just4fun.voc.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Currency {
	
	private static final Currency INSTANCE = new Currency();
	
	private String publishedDate = "";
	
	private Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();

	// waisaaaa, still using xml 
	private String ISO_URL = "http://www.currency-iso.org/dam/downloads/lists/list_one.xml"; 
	
	private Currency() {
		//StringBuilder downloadSource = httpGet();
		StringBuilder downloadSource = readCacheFile();
		parseXMLFile(downloadSource);
	}
	
	private StringBuilder httpGet() {
		URL url;
		try {
			StringBuilder result = new StringBuilder();		
			url = new URL(ISO_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void parseXMLFile(StringBuilder source) {
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			ByteArrayInputStream input = new ByteArrayInputStream(source.toString().getBytes("UTF-8"));
			Document document = builder.parse(input);
			
			publishedDate = document.getDocumentElement().getAttribute("Pblshd");
			
			NodeList nList = document.getElementsByTagName("CcyNtry");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 Node nNode = nList.item(temp);
				 
				   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		               Element eElement = (Element) nNode;
		               
		               String country = eElement.getElementsByTagName("CtryNm").item(0).getTextContent();
		               String currencyName = eElement.getElementsByTagName("CcyNm").item(0).getTextContent();
		               
		               String currency = null;
		               if (eElement.getElementsByTagName("Ccy").item(0) != null)
		            	   currency = eElement.getElementsByTagName("Ccy").item(0).getTextContent();
		               
		               String currencyNumber = null; 
		               if (eElement.getElementsByTagName("CcyNbr").item(0) != null)
		            	   currencyNumber = eElement.getElementsByTagName("CcyNbr").item(0).getTextContent();
		               
		               String currencyMnrUnts = null;
		               if (eElement.getElementsByTagName("CcyMnrUnts").item(0) != null)
		            	   currencyMnrUnts = eElement.getElementsByTagName("CcyMnrUnts").item(0).getTextContent();
		               
		               Map<String, String> d = new HashMap<String, String>();
		               d.put("currencyName", currencyName);
		               d.put("currency", currency);
		               d.put("currencyNumber", currencyNumber);
		               d.put("currencyMnrUnts", currencyMnrUnts);
		               
		               data.put(country, d);

				   }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getPublishedDate() {
		return publishedDate;
	}
	
	public String getCurrencyName(String country) {
		return data.get(country).get("currencyName");
	}
	
	public String getCurrency(String country) {
		return data.get(country).get("currency");
	}
	
	public String getCurrencyNumber(String country) {
		return data.get(country).get("currencyNumber");
	}
	
	public String getCurrencyMnrUnts(String country) {
		return data.get(country).get("currencyMnrUnts");
	}
	
	/**
	 * TODO should really cache source file after download cuz currency iso site does
	 * not like too many http get hit to their servers.   
	 */
	private void cacheSourceFile() {
		
	}
	
	private StringBuilder readCacheFile() {
		StringBuilder sb = new StringBuilder();
		
		try (BufferedReader br = new BufferedReader(new FileReader("currency.cache"))) {
		    
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sb;
	}
	
	
	public static Currency getInstance() {
		return INSTANCE;
	}
	
	public static void main(String[] args) {
		System.out.println(Currency.getInstance().getPublishedDate());
		System.out.println(Currency.getInstance().getCurrency("MALAYSIA"));
		System.out.println(Currency.getInstance().getCurrencyMnrUnts("MALAYSIA"));
		System.out.println(Currency.getInstance().getCurrencyName("MALAYSIA"));
		System.out.println(Currency.getInstance().getCurrencyNumber("MALAYSIA"));
	}
}
