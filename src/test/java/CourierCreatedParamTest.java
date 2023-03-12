import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practicum.yandex.courier.Courier;
import ru.practicum.yandex.courier.CourierClient;
import ru.practicum.yandex.courier.CourierGenerator;
import ru.practicum.yandex.courier.Credentials;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierCreatedParamTest {
    private CourierClient courierClient;
    private Courier courier;
    private int statusCode;
    private String message;
    private int id;

    public CourierCreatedParamTest(Courier courier, int statusCode, String message){
        this.courier = courier;
        this.statusCode = statusCode;
        this.message = message;
    }
    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {CourierGenerator.getWithLoginOnly(),SC_BAD_REQUEST,"Недостаточно данных для создания учетной записи"},
                {CourierGenerator.getWithPasswordOnly(),SC_BAD_REQUEST,"Недостаточно данных для создания учетной записи"}
        };
    }
    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }
    @After
    public void cleanUp(){
        courierClient.delete(id);
    }
    @Test
    @DisplayName("Проверяем ответ при создании курьера с одним пустым полем")
    public void createCourierWithOutOneParameterCheckStatusCode(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");
        int actualStatusCode = responseCreate.extract().path("code");
        String actualMessage = responseCreate.extract().path("message" );
        assertEquals("Неверно",message,actualMessage);
        assertEquals("Неверно",statusCode,actualStatusCode);
    }
}
