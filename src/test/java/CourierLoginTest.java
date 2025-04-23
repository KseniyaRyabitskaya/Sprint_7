import io.restassured.response.Response;
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
        Response response = CourierApi.loginCourier(courierLogin);
        response.then()
                .statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());
    }

    @Test
    public void loginWithoutPassword() {
        Response response = CourierApi.loginCourier(new CourierLogin(courierLogin.getLogin(), ""));
        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithoutLogin() {
        Response response = CourierApi.loginCourier(new CourierLogin("", courierLogin.getPassword()));
        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithoutPasswordAndLogin() {
        Response response = CourierApi.loginCourier(new CourierLogin("", ""));
        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithIncorrectLogin() {
        Response response = CourierApi.loginCourier(new CourierLogin("Ivan", courierLogin.getPassword()));
        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithIncorrectPassword() {
        Response response = CourierApi.loginCourier(new CourierLogin(courierLogin.getLogin(), "57687990876"));
        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithIncorrectPasswordAndLogin() {
        Response response = CourierApi.loginCourier(new CourierLogin("Ivan", "57687990876"));
        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteCourier() {
        Integer id = CourierApi.loginCourier(courierLogin)
                .then()
                .extract().body().path("id");
        CourierApi.deleteCourier(id).then().assertThat().body("ok", equalTo(true));
    }
}
