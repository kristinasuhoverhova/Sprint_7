import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.yandex.courier.Courier;
import ru.practicum.yandex.courier.CourierClient;
import ru.practicum.yandex.courier.CourierGenerator;
import ru.practicum.yandex.courier.Credentials;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourierPositiveTest {
    private CourierClient courierClient;
    private Courier courier;
    private int id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.defaultCourier;
    }

    @After
    public void cleanUp() {
        courierClient.delete(id);
    }

    @Test
    @DisplayName("Проверка ответа когда курьер содан")
    public void courierCanBeCreated() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");
        int actualStatusCode = responseCreate.extract().statusCode();
        boolean isCourierCreated = responseCreate.extract().path("ok");
        assertEquals("Некорректно", actualStatusCode, SC_CREATED);
        assertTrue("Корректно", isCourierCreated);
    }

    @Test
    @DisplayName("Проверка ответа при входе курьера в систему")
    public void courierCanBeLoginAndCheckResponse() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");
        int actualStatusCode = responseLogin.extract().statusCode();
        Assert.assertEquals("Неверно", SC_OK, actualStatusCode);
        Assert.assertNotNull("Вошел", id);
    }
}
