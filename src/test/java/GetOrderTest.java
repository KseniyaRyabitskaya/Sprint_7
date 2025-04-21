import org.example.OrderApi;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderTest {

    @Test
    public void getReturnsListOfOrdersTest() {
        OrderApi.getOrderList().then().assertThat()
                .statusCode(200)
                .and()
                .body("orders", notNullValue());
    }
}
