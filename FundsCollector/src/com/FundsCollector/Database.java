package com.FundsCollector;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Logger;


public class Database {

	 
	    private static final String url = "jdbc:mysql://localhost:3306/mysql";
	    private static final String user = "root";
	    private static final String password = "xxxxx";
	    
	    private static Logger LOGGER = Logger.getLogger("InfoLogging");
		
		public static String currentTimestamp() {
		    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		    DateFormat f = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		    return f.format(c.getTime());
		}
	 
	    public Connection createConnection() {
	        
	    	Connection con = null;
	    	
	    	try {
	            
	    		con = DriverManager.getConnection(url, user, password);
	            //System.out.println("Success");
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    
	    	return con;
	    }
	
	    public boolean closeConnection(Connection con) {
	        
	    	boolean close = false;
	    	
	    	try {
                con.close();
                close = true;
                
            } catch (Exception e) {
                e.printStackTrace();
                
            }
	    	return close;
	    }
	   
	    
	    //Update latest values and calculate floating average
	    public boolean insertDailyDatatoDB(Connection con, String pvm, String value, String table) {
            
    		Connection connection = con;
            Statement stmt = null;
            boolean insertOK = false;
            ResultSet rs = null;
            
            BigDecimal onemounth = null;
            BigDecimal threemounth = null;
            BigDecimal oneyear = null;
            BigDecimal threeyear = null;
            
            float tmp = 0;
            
            //hae kaikki
            try
            {	      
                 
                stmt = connection.createStatement();
                rs = stmt.executeQuery("Select * from " + table);
                LOGGER.info(currentTimestamp() + " Query ok.." );
                
                rs.last();
                
                float sum = Float.parseFloat(value);
                
                //1m
                for (int i= 0; i < 19; i++) {
                	
                	sum = sum + Float.parseFloat(rs.getString("value"));
                	//System.out.println("Counter value lopusta: " + i + " value: " + sum);
                	rs.previous();
                }
                
                tmp = sum / 20;
                onemounth = round(tmp, 2);
                //System.out.println("1m floataverage: " + onemounth);
                sum = 0;
                tmp = 0;
                
                //3m
                rs.last();
                
                sum = Float.parseFloat(value);
                
                for (int i= 0; i < 59; i++) {
                	
                	sum = sum + Float.parseFloat(rs.getString("value"));
                	//System.out.println("Counter value lopusta: " + i + " value: " + sum);
                	rs.previous();
                }
                
                tmp = sum / 60;
                threemounth = round(tmp, 2);
                //System.out.println("3m floataverage: " + threemounth);
                sum = 0;
                tmp = 0;
                
                //1y
                rs.last();
                
                sum = Float.parseFloat(value);
                
                for (int i= 0; i < 239; i++) {
                	
                	sum = sum + Float.parseFloat(rs.getString("value"));
                	//System.out.println("Counter value lopusta: " + i + " value: " + sum);
                	rs.previous();
                }
                
                tmp = sum / 240;
                oneyear = round(tmp, 2);
                //System.out.println("1y floataverage: " + oneyear);
                sum = 0;
                tmp = 0;
                
                //3y
                rs.last();
                
                sum = Float.parseFloat(value);
                
                for (int i= 0; i < 719; i++) {
                	
                	sum = sum + Float.parseFloat(rs.getString("value"));
                	//System.out.println("Counter value lopusta: " + i + " value: " + sum);
                	rs.previous();
                }
                
                tmp = sum / 720;
                threeyear = round(tmp, 2);
                //System.out.println("3y floataverage: " + threeyear);
                sum = 0;
              
                
            }
            catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    stmt.close();
                    //connection.close();
                    insertOK = true;
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }
            }
            
            
            try
            {	      
     
                stmt = connection.createStatement();
                stmt.execute("INSERT INTO " + table + " (pvm,value,1m,3m,1y,3y) "
                                    + "VALUES ('" + pvm + "', '" + value + "', '" + onemounth + "', '" + threemounth + "', '" + oneyear + "', '" + threeyear + "' )");
                LOGGER.info(currentTimestamp() + " Insert done.." );
                
               
            }
            catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    stmt.close();
                    //connection.close();
                    insertOK = true;
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }
            }
            
            return insertOK;
        }
	    
	    //Query for checking the latest values from db
	    public String getLatestValues(Connection con, String table) {
            
    		Connection connection = con;
            Statement stmt = null;
            String latestValues = "";
            ResultSet rs = null;
            
            try
            {	      
                 
                stmt = connection.createStatement();
                rs = stmt.executeQuery("SELECT * FROM " + table);
                LOGGER.info(currentTimestamp() + " Query ok.." );
                
                rs.last();
                
                String pvm = rs.getString("pvm");
                String value = rs.getString("value");
                  
                // print the results
                //System.out.format("%s, %s\n", pvm, value);
                
                latestValues = pvm + "+" + value;
                
            }
            catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    stmt.close();
                    //connection.close();
                    //insertOK = true;
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }
            }
            
            return latestValues;
        }
	    
	    //Roundups for floating avarages
	    public static BigDecimal round(float d, int decimalPlace) {
	        BigDecimal bd = new BigDecimal(Float.toString(d));
	        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
	        return bd;
	    }
}
	    

