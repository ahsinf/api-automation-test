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

public class PersonApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://fakerapi.it/api/v2";
    }

    @Test
    @DisplayName("TC05 - Filter by Gender: Female")
    public void testFilterGenderFemale() {
        Response response = given()
                .queryParam("_quantity", 3)
                .queryParam("_gender", "female")
            .when()
                .get("/persons");

        List<String> genders = response.jsonPath().getList("data.gender");
        
       // Ensures all returned data is female
        for (String gender : genders) {
            assertThat(gender).isEqualTo("female");
        }
    }

    @Test
    @DisplayName("TC06 - Validate Person Details and Address Structure")
    public void testPersonAddressStructure() {
        Response response = given()
                .queryParam("_quantity", 1)
            .when()
                .get("/persons");

        // Retrieve first person data
        Map<String, Object> person = response.jsonPath().getMap("data[0]");
        Map<String, Object> address = (Map<String, Object>) person.get("address");

        // Validate completeness of personal data
        assertThat(person.get("firstname")).isNotNull();
        assertThat(person.get("email")).as("Email should contain @").asString().contains("@");

        // Validasi nested object Address
        assertThat(address.get("city")).isNotNull();
        assertThat(address.get("country")).isNotNull();
    }

    @Test
    @DisplayName("TC07 - Filter by Birthday Start")
    public void testBirthdayFilter() {
        String startDate = "2005-01-01";
        Response response = given()
                .queryParam("_quantity", 2)
                .queryParam("_birthday_start", startDate)
            .when()
                .get("/persons");

        List<String> birthdays = response.jsonPath().getList("data.birthday");

        for (String bday : birthdays) {
            // Ensure year of birth >= 2005
            int birthYear = Integer.parseInt(bday.split("-")[0]);
            assertThat(birthYear).isGreaterThanOrEqualTo(2005);
        }
    }

    @Test
    @DisplayName("TC08 - Negative: Invalid Birthday Format")
    public void testInvalidBirthdayFormat() {
        given()
            .queryParam("_quantity", 1)
            .queryParam("_birthday_start", "01-Jan-2005") // Wrong format (should be YYYY-MM-DD)
        .when()
            .get("/persons")
        .then()
            .statusCode(200); // check whether the API handles errors or gives a default
    }
}