package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PostsApiTest {

    @Before
    public void setup() {
        // Set up base URI for all tests
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    // Positive test case - Get post with ID 1
    @Test
    public void testGetValidPost() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", not(emptyString()))
                .body("body", not(emptyString()));
    }

    // Negative test case 1 - Invalid post ID (non-existent)
    @Test
    public void testGetNonExistentPost() {
        given()
                .when()
                .get("/posts/999999")
                .then()
                .statusCode(404);
    }

    // Negative test case 2 - Invalid post ID (string instead of number)
    @Test
    public void testGetPostWithInvalidIdFormat() {
        given()
                .when()
                .get("/posts/abc")
                .then()
                .statusCode(404);
    }

    // Negative test case 3 - Invalid endpoint
    @Test
    public void testInvalidEndpoint() {
        given()
                .when()
                .get("/invalid_posts/1")
                .then()
                .statusCode(404);
    }

    // Negative test case 4 - Negative ID value
    @Test
    public void testGetPostWithNegativeId() {
        given()
                .when()
                .get("/posts/-1")
                .then()
                .statusCode(404);
    }
}