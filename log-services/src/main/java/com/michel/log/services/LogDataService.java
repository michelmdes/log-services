package com.michel.log.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.michel.log.entities.Log;
import com.michel.log.entities.LogData;
import com.michel.log.repositories.LogDataRepository;
import com.michel.log.services.exceptions.DataIntegrityException;
import com.michel.log.services.exceptions.ObjectNotFoundException;
import com.michel.log.utils.Util;

/**
 * @author Michel Mendes	17/06/2019
 * Classe que contem a regra de negocio da entidade LogData
 */
@Service
public class LogDataService {
	
	@Autowired
	private LogDataRepository repository;
	private static final String DELIMITER_LOG = "|";
	final static Logger logger = Logger.getLogger(LogDataService.class.getName());
	

	public LogData findById(Long id) {
		Optional<LogData> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id ));
	}
	
	public List<LogData> findByLog(Log log) {
		List<LogData> logDataList = null;
		if (log != null) {
			repository.findByLog(log);
		}
		return logDataList;
	}
	
	public Page<LogData> findByLogPage(Long idLog, Integer page, Integer size, String orderBy, String direction) {
		//PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		PageRequest pageRequest = Util.getPagination(page, size, orderBy, direction);
		return repository.findByIdLog(idLog, pageRequest);
	}
	
	public LogData insert(LogData obj) {
		if (obj.getData() != null)
			obj.setData(new Date());
		return repository.save(obj);
	}
	
	public LogData update(Long id, LogData obj) {
		obj.setId(id);
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("You can not delete this object because it contains associated child objects");
		} 
	}
	
	public List<LogData> upload(MultipartFile file, Log log) {
		List<LogData> logDataList = null;
		List<String> errorList = new ArrayList<>();
		if (file != null && !file.isEmpty() && log != null && log.getId() != null) {

			try {
				String line;
				InputStream is = file.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				LogData logData = null;
				Integer i = 1;
				logDataList = new ArrayList<LogData>();
				while ((line = br.readLine()) != null) {
					try {
						logData = parseLogData(line);
						logData.setLog(log);
						logDataList.add(logData);
						
					} catch (Exception e) {
						logger.log(Level.SEVERE, "Error line " + i + " - message: " + e.getMessage());
						errorList.add("Error line " + i + ": " + line);
					} finally {
						i++;
					}
				}
				
				repository.saveAll(logDataList);

			} catch (IOException e) {
				e.printStackTrace();
				throw new ObjectNotFoundException(e.getMessage());
			}
		}
		
		logger.info(errorList.size() + " errors found:");
		for (String error : errorList) {
			logger.log(Level.WARNING, error);
		}
		
		return logDataList;
	}
	
	public LogData parseLogData(String row) throws ParseException {
		LogData logData = null;
		if (row != null && !row.isEmpty()) {
			StringTokenizer st = new StringTokenizer(row, DELIMITER_LOG);
			logData = new LogData();
			
			if (st.hasMoreElements()) {
				String field = (String) st.nextElement();
				logData.setData(Util.parseStringToDate(field, null));
			}
			if (st.hasMoreElements()) {
				String field = (String) st.nextElement();
				logData.setIp(field);
			}
			if (st.hasMoreElements()) {
				String field = (String) st.nextElement();
				logData.setRequest(field);
			}
			if (st.hasMoreElements()) {
				Integer field = Integer.valueOf((String) st.nextElement());
				logData.setStatus(field);
			}
			if (st.hasMoreElements()) {
				String field = (String) st.nextElement();
				logData.setUserAgent(field);
			}
		}
		return logData;
	}
	
}
