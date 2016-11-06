package com.FundsCollector;

import java.sql.Connection;
import java.text.DateFormat;
import java.util.*;
import java.util.logging.Logger;

public class FundTaskCollector extends TimerTask {

	HashMap<String, String> rahastot = new HashMap<String, String>();
	
	public FundTaskCollector(HashMap<String, String> whatfunds) {
		
		this.rahastot = whatfunds;
	}
	
	private static Logger LOGGER = Logger.getLogger("InfoLogging");
	
	public static String currentTimestamp() {
	    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	    DateFormat f = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
	    return f.format(c.getTime());
	}
	
	public void run() {
		
		try {

			DataCollector GetData = new DataCollector();
			
			HashMap<String, String> fundsParameters = new HashMap<String, String>();
			
			
	        Iterator iterator = rahastot.entrySet().iterator();
	        
	       
	        while (iterator.hasNext()) {
	            
	        	Map.Entry me = (Map.Entry) iterator.next();
	            //System.out.println("Key: "+me.getKey() + " & Value: " + me.getValue());
	            
	        	LOGGER.info(currentTimestamp() + "///////////////////////////////////////////////////////////////////// " );
	        	LOGGER.info(currentTimestamp() + " Fund collection starting.. " + me.getKey() );
				
				fundsParameters = GetData.pageParser(me.getValue().toString());
				
				String dayValues = GetData.latestDayValues(me.getValue().toString());
				
				LOGGER.info(currentTimestamp() + " Day value from web: " + dayValues + " :::: " + me.getKey());
				
				Database DB = new Database();
				Connection connect = DB.createConnection();
				
				
				String latestdbValues = DB.getLatestValues(connect, me.getKey().toString());

				LOGGER.info(currentTimestamp() + " Latest value in DB: " + latestdbValues + " :::: " + me.getKey() );
				
				if (dayValues.equals(latestdbValues)) {
					LOGGER.info(currentTimestamp() + " Fund up-to-date in " + me.getKey() + " :::: " + me.getKey() );
					
				}
				else {
					//Update db 
					String temp[] = dayValues.split("\\+");
					String pvm = temp[0];
					String value = temp[1];
					
					boolean okvalue = DB.insertDailyDatatoDB(connect, pvm, value, me.getKey().toString());
					LOGGER.info(currentTimestamp() + " Fund updated to DB " + ":::: " + me.getKey()  );
				}
				
		    
		    	DB.closeConnection(connect);
				}

	            } catch (Exception ex) {

	            	System.out.println("Error running thread " + ex.getMessage());
	            }
		
	}
	
}
