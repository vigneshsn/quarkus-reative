package org.acme.getting.started;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.acme.getting.started.model.Greeting;
import org.acme.getting.started.model.Greetings;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import javax.inject.Inject;

@QuarkusTest
public class ReactiveGreetingResourceTest {

    @Inject
    ObjectMapper objectMapper;


    public static byte[] compress(byte[] body) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(baos)) {
            gzipOutputStream.write(body);
        }
        return baos.toByteArray();
    }


    @Test
    public void testHelloEndpointNonReactive() throws IOException {
        String requestStr = """
                        {
                                
                          "greetings": [
                                {
                                    "name": "hello 0",
                                    "message": "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
                                }
                              ]
                        }
                                
                """;

        //Greetings request = objectMapper.readValue(requestStr, Greetings.class);
        given()
                .body(compress(requestStr.getBytes()))
                .contentType(ContentType.JSON)
                .header(new Header("Content-Encoding", "gzip"))
                .when().post("/hello-non-reactive/greetings")
                .then()
                .statusCode(204);
    }

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(is("hello"));
    }

    @Test
    public void testGreetingEndpoint() {
        String uuid = UUID.randomUUID().toString();
        given()
                .pathParam("name", uuid)
                .when().get("/hello/greeting/{name}")
                .then()
                .statusCode(200)
                .body(is("hello " + uuid));
    }

    @Test
    public void testGreetingsEndpoint() {
        String uuid = UUID.randomUUID().toString();
        given()
                .pathParam("name", uuid)
                .pathParam("count", 2)
                .when().get("/hello/greeting/{count}/{name}")
                .then()
                .assertThat()
                .statusCode(200)
                .body(containsString("hello " + uuid + " - 0"))
                .body(containsString("hello " + uuid + " - 1"));
    }

    @Test
    void generatePayload() throws IOException {
        String filePath = "src/main/resources/data.json";
        Greetings greetings = new Greetings();
        List<Greeting> greetingList = new ArrayList<>();
        for(int i=0; i < 10000; i++) {
            Greeting greeting = new Greeting();
            greeting.setName("hello "+i);
            greeting.setMessage("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
            greetingList.add(greeting);
        }
        greetings.setGreetings(greetingList);

        objectMapper.writeValue(new File(filePath), greetings);
    }

}
