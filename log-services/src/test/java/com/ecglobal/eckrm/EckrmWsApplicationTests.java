package com.ecglobal.eckrm;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public abstract class EckrmWsApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(EckrmWsApplicationTests.class);
	
	@Value("${local.server.port}")
	protected int port;

	@Before
	public void setUp() throws Exception {
		LOG.info("Iniciando teste na porta: " + port);
		RestAssured.port = port;
	}

}

