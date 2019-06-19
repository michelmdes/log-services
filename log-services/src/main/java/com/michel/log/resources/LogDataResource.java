package com.michel.log.resources;

import java.io.Serializable;
import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.michel.log.entities.Log;
import com.michel.log.entities.LogData;
import com.michel.log.services.LogDataService;

import io.swagger.annotations.ApiOperation;

/**
 * @author Michel Mendes	17/06/2019
 * Classe que contem os endpoints REST da api
 * Para acessar a documentacao: http://localhost:8080/swagger-ui.html
 */
@RestController
@RequestMapping(value="/logs/{idLog}/datas")
public class LogDataResource implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private LogDataService service;

	@CrossOrigin
	@ApiOperation(value="Retorna um registro (linha) de um Log (idLog) por id")
	@GetMapping(value="/{id}")
	public ResponseEntity<LogData> findById(@PathVariable Long idLog, @PathVariable Long id) {
		LogData logData = service.findById(id);
		return ResponseEntity.ok().body(logData);
	}
	
	@CrossOrigin
	@ApiOperation(value="Retorna a lista dos registros de um Log (idLog). Resultado paginado.")
	@GetMapping
	public ResponseEntity<Page<LogData>> findPage(@PathVariable Long idLog,
			@RequestParam(value="pageNumber", defaultValue="0") Integer pageNumber, 
			@RequestParam(value="pageSize", defaultValue="10") Integer pageSize, 
			@RequestParam(value="sortName", defaultValue="id") String sortName, 
			@RequestParam(value="sortOrder", defaultValue="DESC") String sortOrder) {
		Page<LogData> list = service.findByLogPage(idLog, pageNumber, pageSize, sortName, sortOrder.toUpperCase());
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@ApiOperation(value="Cria um novo registro de Log")
	@PostMapping
	public ResponseEntity<Void> insert(@PathVariable Long idLog, LogData logData) {
		logData.setId(null);
		logData.setLog(new Log(idLog));
		logData = service.insert(logData);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(logData.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@CrossOrigin
	@ApiOperation(value="Atualiza um registro do Log por id")
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@PathVariable Long idLog, @PathVariable Long id, @Valid LogData logData) {
		logData.setId(id);
		logData.setLog(new Log(idLog));
		logData = service.update(id, logData);
		return ResponseEntity.noContent().build();
	}
	
	@CrossOrigin
	@ApiOperation(value="Exclui um registro do Log por id ")
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long idLog, @PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
