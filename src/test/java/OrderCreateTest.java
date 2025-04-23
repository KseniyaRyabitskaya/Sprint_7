import io.restassured.response.Response;
import org.example.Order;
import org.example.OrderApi;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    Order order;
    int track;

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

    @Test
    public void orderCreationTest() {
        Response response = OrderApi.createOrder(order);
        response.then().assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue())
                .extract().response();
        track = response.path("track");
    }

    @After
    public void cancelOrder() {
        Response response = OrderApi.cancelOrder(track);
        response.then().assertThat().body("ok", equalTo(true));
    }
}
