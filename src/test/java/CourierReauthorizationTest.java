import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.yandex.courier.Courier;
import ru.practicum.yandex.courier.CourierClient;
import ru.practicum.yandex.courier.CourierGenerator;
import ru.practicum.yandex.courier.Credentials;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.junit.Assert.assertEquals;

public class CourierReauthorizationTest {
    private CourierClient courierClient;
    private Courier courier;
    private int id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.defaultCourier;
        ValidatableResponse responseCreate = courierClient.create(courier);
    }
    @After
    public void cleanUp(){
        courierClient.delete(id);
    }
    @Test
    @DisplayName("Проверка ответа когда курьер пытается зарегистрироваться дважды" )
    public void createTheSameCourierAndCheckStatusCode(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");
        int actualCode = responseCreate.extract().path("code" );
        String actualMessage = responseCreate.extract().path("message" );
        assertEquals("Ошибка 409",SC_CONFLICT, actualCode);
        assertEquals("Этот логин уже используется. Попробуйте другой.",actualMessage);
    }
}
