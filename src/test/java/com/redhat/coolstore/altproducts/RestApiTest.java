package com.redhat.coolstore.altproducts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import java.util.Properties;

import org.hamcrest.Matchers;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@DefaultDeployment
@RunWith(Arquillian.class)
public class RestApiTest {
    
    private static String port = System.getProperty("arquillian.swarm.http.port", "18080");
    
    @CreateSwarm
    public static Swarm newContainer() throws Exception {
        Properties properties = new Properties();
        properties.put("swarm.http.port", port);
        return new Swarm(properties).withProfile("local");
    }
    
    @Before
    public void beforeTest() throws Exception {
        RestAssured.baseURI = String.format("http://localhost:%s", port);
    }
    
    @Test
    @RunAsClient
    public void testAltProducts() throws Exception {
        given()
            .get("/altproduct/{itemId}", "329199")
            .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("itemId", equalTo("329199"))
            .body("altItemIds", hasItems("629199"));
    }
    
    @Test
    @RunAsClient
    public void testAltProductsWhenNoAlternatives() throws Exception {
        given()
            .get("/altproduct/{itemId}", "429199")
            .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("itemId", equalTo("429199"))
            .body("altItemIds", Matchers.hasSize(0));
    }

    @Test
    @RunAsClient
    public void testHealthCheckCombined() throws Exception {
        given()
            .get("/health")
            .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("outcome", equalTo("UP"));
    }
    

}
