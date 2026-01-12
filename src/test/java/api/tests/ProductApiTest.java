package test.java.api.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://fakerapi.it/api/v2";
    }

    @Test
    @DisplayName("TC01 - Verify successful response and basic structure")
    public void testGetProductsSuccess() {
        given()
            .queryParam("_quantity", 5)
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("status", equalTo("OK"))
            .body("code", equalTo(200))
            .body("total", equalTo(5));
    }

    @Test
    @DisplayName("TC02 - Validate data consistency and business logic")
    public void testProductDataValidation() {
        Response response = given()
            .queryParam("_quantity", 3)
        .when()
            .get("/products");

        // Retrieves data as a List of Maps
        List<Map<String, Object>> products = response.jsonPath().getList("data");

        // AssertJ: Ensure that the list size matches the request
        assertThat(products).hasSize(3);

        // Validate specific fields on the first item
        Map<String, Object> firstProduct = products.get(0);
        
        assertThat(firstProduct.get("name")).as("Product name should not be null").isNotNull();
        assertThat(firstProduct.get("price")).as("Price should be a string/number").isNotNull();
        
        // Image format validation (URL)
        String imageUrl = (String) firstProduct.get("image");
        assertThat(imageUrl).startsWith("http");
    }

    @Test
    @DisplayName("TC03 - Validate negative case with zero quantity")
    public void testInvalidQuantity() {
        System.out.println("LOG: Checking behavior for _quantity=0...");

        Response response = given()
                .queryParam("_quantity", 0)
            .when()
                .get("/products");

        int totalActual = response.jsonPath().getInt("total");

        // If total is not 0, print a warning to the log.
        if (totalActual != 0) {
            System.err.println("BUG DETECTED: API should return total 0 for quantity 0, but returned: " + totalActual);
        }

        // Assertions are left with an expectation of 0 so that test failures indicate the presence of a bug.
        assertThat(totalActual)
                .as("BUG FOUND: FakerAPI returns default 10 items instead of 0 when _quantity is 0")
                .isEqualTo(0);
    }

    @Test
    @DisplayName("TC04 - Negative: Exceed Maximum Quantity Limit")
    public void testExceedMaxQuantity() {
        given()
            .queryParam("_quantity", 1001) // Exceeded the limit of 1000
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("total", lessThanOrEqualTo(1000)); 
    }
}