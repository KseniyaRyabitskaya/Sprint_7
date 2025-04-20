import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    Order order;

    public OrderCreateTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {
                        new Order(
                                "Ivan",
                                "Ivanov",
                                "Komsomolskaya, 4",
                                2,
                                "+7 800 355 67 67",
                                3,
                                "2025-05-01",
                                "Привет!",
                                new String[]{"BLACK", "GREY"}
                        )
                },
                {
                        new Order(
                                "Petr",
                                "Fedorin",
                                "Lenina, 7",
                                4,
                                "+7 800 355 72 72",
                                2,
                                "2025-06-03",
                                "Привозите, быстрее",
                                new String[]{""}
                        )
                },
                {
                        new Order(
                                "Alex",
                                "Sidodrov",
                                "Bakunina, 6",
                                1,
                                "+7 800 355 33 33",
                                2,
                                "2025-07-01",
                                "Don't call",
                                new String[]{"GREY"}
                        )
                },
                {
                        new Order(
                                "Fedor",
                                "Petrov",
                                "Pushkina, 78",
                                4,
                                "+7 800 355 34 34",
                                3,
                                "2025-05-03",
                                "Жду",
                                new String[]{"BLACK"}
                        )
                }
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void orderCreationTest() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(order)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }
}
