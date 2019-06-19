package com.michel.log.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.michel.log.entities.Log;
import com.michel.log.entities.LogData;
import com.michel.log.repositories.LogRepository;
import com.michel.log.services.exceptions.DataIntegrityException;
import com.michel.log.services.exceptions.ObjectNotFoundException;
import com.michel.log.utils.Util;

/**
 * @author Michel Mendes	17/06/2019
 * Classe que contem a regra de negocio da entidade Log
 */
@Service
public class LogService {

	@Autowired
	private LogRepository repository;
	@Autowired
	private LogDataService logDataService;
	final static Logger logger = Logger.getLogger(LogService.class.getName());

	
	public Log findById(Long id) {
		Optional<Log> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id ));
	}
	
	public Page<Log> findPage(Integer page, Integer size, String orderBy, String direction) {
		//PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		PageRequest pageRequest = Util.getPagination(page, size, orderBy, direction);
		return repository.findAll(pageRequest);
	}

	public Log insert(Log obj) {
		obj.setData(new Date());
		return repository.save(obj);
	}
	
	public Log update(Long id, Log obj) {
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
	
	public Log upload(MultipartFile file) {
		Long dateBegin = new Date().getTime();
		Log log = new Log();
		log.setFileName(file.getOriginalFilename());
		log.setDescription("Imported file: " + file.getOriginalFilename());
		log = insert(log);
		
		List<LogData> logDataList = logDataService.upload(file, log);
		log.setLogDataList(logDataList);
		Long intervSeg = (new Date().getTime() - dateBegin) / 1000;
		logger.info("Arquivo de log '"+file.getOriginalFilename()+"' importado em "+intervSeg+" segundos");
		return log;
	}
	
}
