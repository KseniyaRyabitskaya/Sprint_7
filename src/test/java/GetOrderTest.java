import io.restassured.response.Response;
import org.example.OrderApi;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderTest {

    @Test
    public void getReturnsListOfOrdersTest() {
        Response response = OrderApi.getOrderList();
        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("orders", notNullValue());
    }
}
