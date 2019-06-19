package com.michel.log.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

public class Util {
	
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	
    public static String parseDateToString(Date data, String formato) {
    	String dataFormatada = null;
    	SimpleDateFormat formatador = null;
    	if (data != null) {
	    	if (formato == null || formato.isEmpty()) {
	    		formato = DATE_TIME_PATTERN;
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
    		formato = DATE_TIME_PATTERN;
    	}
    	if (data != null) {
			formatador = new SimpleDateFormat(formato);
			dataFormatada = formatador.parse(data);
    	}
    	return dataFormatada;
    }
    
    public static PageRequest getPagination(Integer pageNumber, Integer pageSize, String sortName, String sortOrder){	
		
		/* 
		 * Tratando pageNumber.
		 * O bootstrab table tem primeira pagina 1, e ou pageRequest do Spring tem primeira pagina 0
		 */
		Integer pPageNumber = 0; // Default
		if (pageNumber != null && pageNumber > 0)
			pPageNumber = pageNumber - 1;
		
		/* 
		 * Tratando sortOrder.
		 * Duas opcoes possiveis: ASC e DESC
		 * Padrao: ASC
		 */
		Direction direction = Direction.ASC;
		if (Direction.DESC.name().equalsIgnoreCase(sortOrder))
			direction = Direction.DESC;
		
		/* 
		 * Tratando pageSize.
		 * Padrao: 10
		 */
		Integer pPageSize = 10;
		if (pageSize != null && pageSize > 0) {
			pPageSize = pageSize;
		}
		
		/* 
		 * Tratando sortName.
		 * Padrao: "id"
		 */
		String pSortName = "id";
		if (sortName != null && !sortName.isEmpty()) {
			pSortName = sortName;
		}
		
		PageRequest pageRequest = PageRequest.of(pPageNumber, pPageSize, direction, pSortName);
		
	    return pageRequest;
	}

}
