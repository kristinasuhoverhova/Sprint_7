package ru.practicum.yandex.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.practicum.yandex.Client;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {
    private static final String PATH = "/api/v1/courier/";
    private static final String PATH_LOGIN = "/api/v1/courier/login/";
    private static final String PATH_DELETE = "/api/v1/courier/";

    @Step("Создание Курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Логин Курьера")
    public ValidatableResponse login(Credentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(PATH_LOGIN)
                .then();
    }

    @Step("Удаление Курьера")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getSpec())
                .when()
                .delete(PATH_DELETE + id)
                .then();
    }
}
