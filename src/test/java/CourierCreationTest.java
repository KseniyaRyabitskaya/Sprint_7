import io.qameta.allure.Step;
import org.example.Courier;
import org.example.CourierLogin;
import org.example.CourierApi;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreationTest {
    Courier courier;

    @Before
    public void setUp() {
        courier = new Courier("Ponchic11111", "12345", "Alex");
    }

    @Test
    public void createCourierTest() {
        CourierApi.createCourier(courier).then().assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
        deleteCourier();
    }

    @Test
    public void createIdenticalCourierTest() {
        CourierApi.createCourier(courier);
        CourierApi.createCourier(courier).then().assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        deleteCourier();
    }

    @Test
    public void createCourierWithoutParamsTest() {
        createCourierWithoutPassword();
        createCourierWithoutLogin();
        createCourierWithoutLoginAndPassword();
    }

    @Step
    private void createCourierWithoutPassword() {
        CourierApi.createCourier(new Courier("Ponchic902", "", "Alex")).then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step
    private void createCourierWithoutLogin() {
        CourierApi.createCourier(new Courier("", "12345", "Alex")).then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step
    private void createCourierWithoutLoginAndPassword() {
        CourierApi.createCourier(new Courier("", "", "")).then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step
    private void deleteCourier() {
        Integer id = CourierApi.loginCourier(
                        new CourierLogin(
                                courier.getLogin(),
                                courier.getPassword()
                        )
                )

                .then()
                .extract()
                .body()
                .path("id");

        CourierApi.deleteCourier(id);
    }
}
