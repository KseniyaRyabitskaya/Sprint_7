import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void getReturnsListOfOrdersTest() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .queryParam("limit", 10)
                        .queryParam("page", 1)
                        .when()
                        .get("/api/v1/orders");

        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}
