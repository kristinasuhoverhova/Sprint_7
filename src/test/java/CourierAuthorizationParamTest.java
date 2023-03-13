import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practicum.yandex.courier.Courier;
import ru.practicum.yandex.courier.CourierClient;
import ru.practicum.yandex.courier.CourierGenerator;
import ru.practicum.yandex.courier.Credentials;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierAuthorizationParamTest {
    private CourierClient courierClient;
    private final Courier courier;
    private final int statusCode;
    private final String message;

    public CourierAuthorizationParamTest(Courier courier, int statusCode, String message) {
        this.courier = courier;
        this.statusCode = statusCode;
        this.message = message;
    }

    @Parameterized.Parameters(name = "#{index}, {0}, {1}, {2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {CourierGenerator.getWithLoginOnly(), SC_BAD_REQUEST, "Недостаточно данных для входа"},
                {CourierGenerator.getWithPasswordOnly(), SC_BAD_REQUEST, "Недостаточно данных для входа"},
                {CourierGenerator.getWithIncorrectValue(), SC_NOT_FOUND, "Учетная запись не найдена"}
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Авторизация курьера с одним пустым полем")
    public void loginCourierAndCheckStatusCode() {
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        int actualStatusCode = responseLogin.extract().statusCode();
        String actualMessage = responseLogin.extract().path("message");
        assertEquals("Неверно", statusCode, actualStatusCode);
        assertEquals("Неверно", message, actualMessage);
    }
}
