import io.qameta.allure.Step;
import org.example.Courier;
import org.example.CourierLogin;
import org.example.CourierApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {

    Courier courier;
    CourierLogin courierLogin;

    @Before
    public void setUp() {
        courier = new Courier("deliveryman", "1deliveryman1", "Ivan");
        courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        CourierApi.createCourier(courier);
    }

    @Test
    public void courierAuthorisationSuccessTest() {
        CourierApi.loginCourier(courierLogin).then()
                .statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());
    }

    @Test
    public void courierAuthorisationWithoutAnyParamsTest() {
        loginWithoutPassword();
        loginWithoutLogin();
        loginWithoutPasswordAndLogin();
    }

    @Test
    public void courierAuthorisationWithoutIncorrectParamsTest() {
        loginWithIncorrectPassword();
        loginWithIncorrectLogin();
        loginWithIncorrectPasswordAndLogin();
    }

    @Step
    private void loginWithoutPassword() {
        CourierApi.loginCourier(new CourierLogin(courierLogin.getLogin(), "")).then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step
    private void loginWithoutLogin() {
        CourierApi.loginCourier(new CourierLogin("", courierLogin.getPassword())).then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step
    private void loginWithoutPasswordAndLogin() {
        CourierApi.loginCourier(new CourierLogin("", "")).then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step
    private void loginWithIncorrectLogin() {
        CourierApi.loginCourier(new CourierLogin("Ivan", courierLogin.getPassword())).then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step
    private void loginWithIncorrectPassword() {
        CourierApi.loginCourier(new CourierLogin(courierLogin.getLogin(), "57687990876")).then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step
    private void loginWithIncorrectPasswordAndLogin() {
        CourierApi.loginCourier(new CourierLogin("Ivan", "57687990876")).then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteCourier() {

        Integer id = CourierApi.loginCourier(courierLogin)
                .then()
                .extract().body().path("id");

        CourierApi.deleteCourier(id);
    }
}
