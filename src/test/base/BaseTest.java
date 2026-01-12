import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    @BeforeAll
    public static void setup() {
        // Setup base URL sekali untuk semua test class yang meng-extend ini
        RestAssured.baseURI = "https://fakerapi.it/api/v1";
    }
}