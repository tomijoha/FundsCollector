package com.FundsCollector;


import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataCollector {
	
	
  public HashMap<String, String> pageParser(String url) {
	
	  HashMap<String, String> funds = new HashMap<String, String>();  
	  
	try {
 
		// need http protocol
		Document doc = Jsoup.connect(url).get();
		
		
		// get page title
		String title = doc.title();
		//System.out.println("title : " + title);
 
		
		Elements contents = doc.getElementsByClass("Width15");
		for (Element content : contents) {
			
			
			String value = content.text();
			//Start parsing values
			Element siblingfirst = content.firstElementSibling();
			Element siblingsecond = content.nextElementSibling();
			//Day and value
			String first = siblingfirst.text();
			String second = siblingsecond.text();
			
			funds.put(first, second);
			
			
		}
		
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	return funds;
	
  }//pageParser
  
  public String latestDayValues(String url) {
		
	  String dayfunds = "";  
	  
	try {
 
		// need http protocol
		Document doc = Jsoup.connect(url).get();
		
		// get page title
		String title = doc.title();
		//System.out.println("title : " + title);
 
		
		Elements contents = doc.getElementsByClass("Width15");
		Element content = null;
		
		
		for (int i = 0; i < 2; i++) {
			
			content = contents.get(i);
			
			if (i == 1) {
			
			Element siblingfirst = content.firstElementSibling();
			Element siblingsecond = content.nextElementSibling();
			
			//Take day and the value
			String first = siblingfirst.text();
			String second = siblingsecond.text();
			
			dayfunds = first + "+" + second.replace(",", "."); 
			
			}
			
		}
		
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	return dayfunds;
	
  }//latest day parser
  
  
}
