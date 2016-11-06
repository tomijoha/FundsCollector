package com.FundsCollector;

import java.util.HashMap;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FundsCollector {
	
	private static Logger LOGGER = Logger.getLogger("InfoLogging");
	
	public static String currentTimestamp() {
	    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	    DateFormat f = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
	    return f.format(c.getTime());
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		FileHandler fh;  
		try {  

	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler("/home/server/Desktop/funds/MyLogFile.log");  
	        LOGGER.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        // the following statement is used to log any messages  
	        LOGGER.info("My first log");  

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } 
		
		LOGGER.info(currentTimestamp() + " Starting.." );
		
		HashMap<String, String> rahastot = new HashMap<String, String>();
		
		//Funds
		//String testitaulu = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/kurssit-ja-markkinat/markkinat?sym=101639284&id=32462&srcpl=3";
		String OPKorkotuotto = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=42992016&id=39112&srcpl=3"; 
		String OPDelta = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=18513219&id=39112&srcpl=3";
		String OPEurooppaosake = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=18512978&id=39112&srcpl=3";
		String OPFocus = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=18513227&id=39112&srcpl=3";
		String OPHenkiaasiaindeksi = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=52578771&id=39112&srcpl=3";
		String OPHenkiamerikkaindeksi = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=53158275&id=39112&srcpl=3";
		String OPHenkieurooppaindeksi = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=53158273&id=39112&srcpl=3";
		String OPHenkilatinalainenamerikka = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=47356856&id=39112&srcpl=3";
		String OPHenkipohjoismaatindeksi = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=53158274&id=39112&srcpl=3";
		String OPIlmasto = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=18513233&id=39112&srcpl=3";
		String OPIntia = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=18513396&id=39112&srcpl=3";
		String OPJapani = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=18513409&id=39112&srcpl=3";
		String OPKehittyvatosakemarkkinat = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=31699865&id=39112&srcpl=3";
		String OPKiina = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=18513235&id=39112&srcpl=3";
		String OPKiinteisto = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=20049600&id=39112&srcpl=3";
		String OPMaailma = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=20049496&id=39112&srcpl=3";
		String OPSuomipienyhtiot = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=18513254&id=39112&srcpl=3";
		String OPPuhdasvesi = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=73111928&id=39112&srcpl=3";
		String OPRaakaaine = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=18513398&id=39112&srcpl=3";
		String OPRohkea = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=18513241&id=39112&srcpl=3";
		String OPSuomiarvo = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=18512980&id=39112&srcpl=3";
		String OPTaktinensalkku = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=18513251&id=39112&srcpl=3";
		String OPVenaja = "https://www.op.fi/op/henkiloasiakkaat/saastot-ja-sijoitukset/vakuutussaastamisen-kurssit?sym=18512992&id=39112&srcpl=3";
		
		
		
		//rahastot.put("testitaulu", testitaulu);
		rahastot.put("OPKorkotuotto", OPKorkotuotto);
		rahastot.put("OPDelta", OPDelta);
		rahastot.put("OPEurooppaosake", OPEurooppaosake);
		rahastot.put("OPFocus", OPFocus);
		rahastot.put("OPHenkiaasiaindeksi", OPHenkiaasiaindeksi);
		rahastot.put("OPHenkiamerikkaindeksi", OPHenkiamerikkaindeksi);
		rahastot.put("OPHenkieurooppaindeksi", OPHenkieurooppaindeksi);
		rahastot.put("OPHenkilatinalainenamerikka", OPHenkilatinalainenamerikka);
		rahastot.put("OPHenkipohjoismaatindeksi", OPHenkipohjoismaatindeksi);
		rahastot.put("OPIlmasto", OPIlmasto);
		rahastot.put("OPIntia", OPIntia);
		rahastot.put("OPJapani", OPJapani);
		rahastot.put("OPKehittyvatosakemarkkinat", OPKehittyvatosakemarkkinat);
		rahastot.put("OPKiina", OPKiina);
		rahastot.put("OPKiinteisto", OPKiinteisto);
		rahastot.put("OPMaailma", OPMaailma);
		rahastot.put("OPSuomipienyhtiot", OPSuomipienyhtiot);
		rahastot.put("OPPuhdasvesi", OPPuhdasvesi);
		rahastot.put("OPRaakaaine", OPRaakaaine);
		rahastot.put("OPRohkea", OPRohkea);
		rahastot.put("OPSuomiarvo", OPSuomiarvo);
		rahastot.put("OPTaktinensalkku", OPTaktinensalkku);
		rahastot.put("OPVenaja", OPVenaja);
		
		
		Timer time = new Timer(); // Instantiate Timer Object
		FundTaskCollector st = new FundTaskCollector(rahastot); // Instantiate SheduledTask class
		time.schedule(st, 0, 21600*1000); // Create Repetitively task
		//once in 6h = 21600s
		
			
	}

}
