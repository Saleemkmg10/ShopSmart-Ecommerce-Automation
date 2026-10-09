
package com.shopsmart.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void createProductTest() {

        String requestBody = """
                {
                  "name": "Automation Keyboard",
                  "description": "Created by REST Assured",
                  "price": 1299.00,
                  "stockQuantity": 20,
                  "category": "Electronics"
                }
                """;

        Response response =
                given()
                        .contentType("application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/products");

        response.then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("Automation Keyboard"))
                .body("category", equalTo("Electronics"));

        System.out.println("Created product:");
        response.prettyPrint();
    }

    @Test
    public void getAllProductsTest() {
        given()
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("$", not(empty()));

        System.out.println("Product list API passed");
    }

    @Test
    public void getMissingProductTest() {
        given()
                .when()
                .get("/api/products/99999")
                .then()
                .statusCode(404);

        System.out.println("Missing product API passed");
    }

    @Test
    public void updateProductTest() {
        String body = """
            {
              "name": "Update Test Product",
              "description": "Testing product update",
              "price": 500.00,
              "stockQuantity": 10,
              "category": "Testing"
            }
            """;

        Response created =
                given()
                        .contentType("application/json")
                        .body(body)
                        .when()
                        .post("/api/products");

        created.then().statusCode(201);

        int id = created.jsonPath().getInt("id");

        String updatedBody = """
            {
              "name": "Updated ShopSmart Product",
              "description": "Updated successfully",
              "price": 750.00,
              "stockQuantity": 15,
              "category": "Electronics"
            }
            """;

        given()
                .contentType("application/json")
                .body(updatedBody)
                .when()
                .put("/api/products/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated ShopSmart Product"));

        System.out.println("Update product API passed");
    }


    @Test
    public void deleteProductTest() {
        String body = """
            {
              "name": "Delete Test Product",
              "description": "Testing product deletion",
              "price": 100.00,
              "stockQuantity": 5,
              "category": "Testing"
            }
            """;

        Response created =
                given()
                        .contentType("application/json")
                        .body(body)
                        .when()
                        .post("/api/products");

        created.then().statusCode(201);

        int id = created.jsonPath().getInt("id");

        given()
                .when()
                .delete("/api/products/" + id)
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/api/products/" + id)
                .then()
                .statusCode(404);

        System.out.println("Delete product API passed");
    }
}