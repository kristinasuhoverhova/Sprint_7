import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practicum.yandex.order.Order;
import ru.practicum.yandex.order.OrderClient;
import ru.practicum.yandex.order.OrderGenerator;

import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(Parameterized.class)
public class OrderCreatedParamTest {

    private OrderClient orderClient;
    private Order order;
    private int statusCode;

    public OrderCreatedParamTest(Order order, int statusCode) {
        this.order = order;
        this.statusCode = statusCode;
    }
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {OrderGenerator.getWithBlack(), SC_CREATED},
                {OrderGenerator.getWithGrey(), SC_CREATED},
                {OrderGenerator.getWithoutTwoColours(), SC_CREATED},
                {OrderGenerator.getWithTwoColours(), SC_CREATED}
        };
    }
    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }
    @Test
    public void orderCanBeCreated() {
        ValidatableResponse responseCreate = orderClient.create(order);
        int actualStatusCode = responseCreate.extract().statusCode();
        int track = responseCreate.extract().path("track");
        Assert.assertNotNull("Появился номер", track);
        Assert.assertEquals("Неверный статус-код", statusCode, actualStatusCode);
    }
}
