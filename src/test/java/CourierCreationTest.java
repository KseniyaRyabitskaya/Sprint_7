import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class CourierCreationTest {
    Courier courier;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        courier = new Courier("Ponchic11111", "12345", "Alex");
    }

    @Test
    public void getStatusCodeForCourierCreationTest() {
        createFirstCourier();
        createIdenticalCourier();
        createCourierWithoutPassword();
        createCourierWithoutLogin();
        createCourierWithoutLoginAndPassword();
    }

    @Step
    private void createFirstCourier() {
        createCourierResponse(courier).then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Step
    private void createIdenticalCourier() {
        createCourierResponse(courier).then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @Step
    private void createCourierWithoutPassword() {
        createCourierResponse(new Courier("Ponchic902", "", "Alex")).then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Step
    private void createCourierWithoutLogin() {
        createCourierResponse(new Courier("", "12345", "Alex")).then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Step
    private void createCourierWithoutLoginAndPassword() {
        createCourierResponse(new Courier("", "", "")).then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    private Response createCourierResponse(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @After
    public void deleteCourier() {
        Integer id =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login")
                        .then()
                        .extract().body().path("id");

        given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete("/api/v1/courier/" + id);

    }
}
