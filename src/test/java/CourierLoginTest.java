import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Courier;
import org.example.CourierLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {

    Courier courier;
    CourierLogin courierLogin;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        courier = new Courier("deliveryman", "1deliveryman1", "Ivan");
        courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Test
    public void courierAuthorisationTest() {
        loginSuccess();
        loginWithoutPassword();
        loginWithoutLogin();
        loginWithoutPasswordAndLogin();
        loginWithIncorrectPassword();
        loginWithIncorrectLogin();
        loginWithIncorrectPasswordAndLogin();
    }

    @Step
    private void loginSuccess() {
        loginCourier(courierLogin).then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Step
    private void loginWithoutPassword() {
        loginCourier(new CourierLogin(courierLogin.getLogin(), "")).then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Step
    private void loginWithoutLogin() {
        loginCourier(new CourierLogin("", courierLogin.getPassword())).then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Step
    private void loginWithoutPasswordAndLogin() {
        loginCourier(new CourierLogin("", "")).then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Step
    private void loginWithIncorrectLogin() {
        loginCourier(new CourierLogin("Ivan", courierLogin.getPassword())).then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Step
    private void loginWithIncorrectPassword() {
        loginCourier(new CourierLogin(courierLogin.getLogin(), "57687990876")).then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Step
    private void loginWithIncorrectPasswordAndLogin() {
        loginCourier(new CourierLogin("Ivan", "57687990876")).then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    private Response loginCourier(CourierLogin courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
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
