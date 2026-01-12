package test.java.api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://fakerapi.it/api/v1";
    }

    @Test
    @DisplayName("TC09 - Endpoint Companies - Get Success")
    public void testGetCompaniesSuccess() {
        given()
            .queryParam("_quantity", 1)
        .when()
            .get("/companies")
        .then()
            .statusCode(200)
            .contentType("application/json");
    }

    @Test
    @DisplayName("TC10 - Validate Company Contact Details")
    public void testCompanyContactDetails() {
        Response response = given()
                .queryParam("_quantity", 2)
            .when()
                .get("/companies");

        List<Map<String, Object>> companies = response.jsonPath().getList("data");

        for (Map<String, Object> company : companies) {
            assertThat(company.get("email")).asString().contains("@");
            assertThat(company.get("phone")).isNotNull();
            assertThat(company.get("website")).asString().startsWith("http");
        }
    }

    @Test
    @DisplayName("TC11 - Validate Country Data in Companies")
    public void testCompanyCountryData() {
        Response response = given()
                .queryParam("_quantity", 1)
            .when()
                .get("/companies");

        // Get nested object 'country' inside 'address'
        String country = response.jsonPath().getString("data[0].address.country");
        
        assertThat(country).as("Country should not be null or empty").isNotEmpty();
        System.out.println("LOG: Validated company located in: " + country);
    }
}