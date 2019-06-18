package com.ecglobal.eckrm.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.ecglobal.eckrm.EckrmWsApplicationTests;

import static io.restassured.RestAssured.when;

/**
 * Classe responsavel por testar os endpoints ProjectStatusResource
 * @author Michel Mendes
 * @data 17/01/2019
 */
public class ProjectStatusResourceTest extends EckrmWsApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(ProjectStatusResourceTest.class);
	
	@Test
	public void listar_primeira_pag_projectStatus() throws Exception{
		LOG.info("Teste listar_primeira_pag_projectStatus");
		 when().
         	get("/project-status/page").
         then().
	         log().body().and()
	         .statusCode(HttpStatus.OK.value());
	         //body("lotto.lottoId", equalTo(5),
             // "lotto.winners.winnerId", hasItems(23, 54));
	}
	
}
