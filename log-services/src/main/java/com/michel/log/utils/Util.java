package com.michel.log.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	
	public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	
    public static String parseDateToString(Date data, String formato) {
    	String dataFormatada = null;
    	SimpleDateFormat formatador = null;
    	if (data != null) {
	    	if (formato == null || formato.isEmpty()) {
	    		formato = DATE_PATTERN;
	    	}
    		formatador = new SimpleDateFormat(formato);
    		dataFormatada = formatador.format(data);
    	}
    	return dataFormatada;
    }
    
    public static Date parseStringToDate(String data, String formato) throws ParseException {
    	Date dataFormatada = null;
    	SimpleDateFormat formatador = null;
    	if (formato == null || formato.isEmpty()) {
    		formato = DATE_PATTERN;
    	}
    	if (data != null) {
			formatador = new SimpleDateFormat(formato);
			dataFormatada = formatador.parse(data);
    	}
    	return dataFormatada;
    }

}
