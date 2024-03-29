package com.michel.log.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.michel.log.entities.Log;
import com.michel.log.entities.LogData;

/**
 * @author Michel Mendes	17/06/2019
 * Classe de acesso ao repositorio LogData
 */
@Repository
public interface LogDataRepository extends JpaRepository<LogData, Long>{

	List<LogData> findByLog(Log log);
	Page<LogData> findByLog(Log log, PageRequest pageRequest);
	
	@Query("SELECT d FROM LogData d WHERE (?1 is null or d.log.id = ?1)")
	Page<LogData> findByIdLog(Long log, PageRequest pageRequest);
	
}
