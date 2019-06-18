package com.michel.log.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michel.log.entities.Log;

/**
 * @author Michel Mendes	17/06/2019
 * Classe de acesso ao repositorio Log
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Long>{

}
