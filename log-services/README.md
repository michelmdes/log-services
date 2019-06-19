#Log-Services (Backend do sistema Log-manager)

## Tecnologias
Spring Boot 2.1.1
Swagger 2.9.2

## Documentação
Gerada automaticamente através do Swagger
Link: [ApplicationPath]/swagger-ui.html

### Tratamento de exceções
No arquivo ResourceExceptionHandler.java ( com.michel.log.resources.exceptions ) é configurado cada tipo de exceção, um retorno http e uma mensagem padrão 

### Criando banco de dados
´´´
CREATE DATABASE log;
CREATE SCHEMA log_manager;

CREATE TABLE log_manager.log (
	id BIGSERIAL NOT NULL,
	file_name text NOT NULL,
	description text NULL,
	"data" timestamp NOT NULL,
	CONSTRAINT log_pk PRIMARY KEY (id)
);

CREATE TABLE log_manager.log_data (
	id BIGSERIAL NOT NULL,
	"data" timestamp NOT NULL,
	ip varchar NOT NULL,
	request text NOT NULL,
	status int NULL,
	user_agent varchar NULL,
	fk_log bigint NOT NULL,
	CONSTRAINT log_data_pk PRIMARY KEY (id),
	CONSTRAINT log_data_fk FOREIGN KEY (fk_log) REFERENCES log_manager.log(id)
);
´´´

Michel Mendes
michelmdes@gmail.com
