package com.fator3.nudoor;

import javax.annotation.PostConstruct;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    @LocalServerPort
    private int PORT;
    private static String BASE_PATH = "/api/nudoor/";
    private static String BASE_HOST = "http://localhost";

    @PostConstruct
    public void setUp() {
        RestAssured.basePath = BASE_PATH;
        RestAssured.baseURI = BASE_HOST;
        RestAssured.port = PORT;
    }

}
