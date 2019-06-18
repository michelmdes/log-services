package com.michel.log.resources;

import java.io.Serializable;
import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.michel.log.entities.Log;
import com.michel.log.services.LogService;

import io.swagger.annotations.ApiOperation;

/**
 * @author Michel Mendes	17/06/2019
 * Classe que contem os endpoints REST da api
 * Para acessar a documentacao: http://localhost:8080/swagger-ui.html
 */
@RestController
@RequestMapping(value="/logs")
public class LogResource implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private LogService service;

	
	@ApiOperation(value="Retorna um Log por id")
	@GetMapping(value="/{id}")
	public ResponseEntity<Log> findById(@PathVariable Long id) {
		Log log = service.findById(id);
		return ResponseEntity.ok().body(log);
	}
	
	@ApiOperation(value="Retorna a lista dos Logs. Resultado paginado.")
	@GetMapping
	public ResponseEntity<Page<Log>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="size", defaultValue="10") Integer size, 
			@RequestParam(value="orderBy", defaultValue="id") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Log> list = service.findPage(page, size, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
	
	@ApiOperation(value="Cria um novo Log")
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Log log) {
		log.setId(null);
		log = service.insert(log);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(log.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value="Atualiza um Log por id")
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody Log log) {
		log = service.update(id, log);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value="Exclui um Log por id ")
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value="Importa um arquivo de Log, criando um Log e todos os seus dados.",
			notes = "Formato LOG\n" + 
					"-----------\n" + 
					"Data, IP, Request, Status, User Agent (delimitado por aspas duplas);\n" + 
					"O delimitador do arquivo de log é o caracter pipe (|);\n" + 
					"Formato de data: \"yyyy-MM-dd HH:mm:ss.SSS\";")
	@PostMapping("/upload")
	public ResponseEntity<Void> upload(@RequestParam MultipartFile file) {
		Log log = service.upload(file);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(log.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
}
