package tests;

import endpoints.UserEndpoints;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import specs.CustomSpec;
import testData.TestData;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.CustomSpec.*;

public class PostUserTests extends TestBase {

    @Test
    @Description("Отправка POST запроса и создание пользователя")
    @DisplayName("Проверка успешного создания нового пользователя")
    @Tag("ApiTests")
    @Step("Отправка запроса на создание нового пользователя")
    void createUserTestPositive() {

        CreateUserResponse response = given(CustomSpec.requestSpec)
                .body(TestData.validCreateData)
                .when()
                .post(UserEndpoints.CREATE_USER.getEndpoint())
                .then()
                .spec(responseSpec201)
                .extract().as(CreateUserResponse.class);

        checkCreateUserResponse(response);
    }

    @Step("Проверка успешного создания пользователя")
    private void checkCreateUserResponse(CreateUserResponse response) {
        assertThat(response.getName()).isEqualTo(TestData.expectedName);
        assertThat(response.getJob()).isEqualTo(TestData.expectedJob);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getCreatedAt()).isNotNull();
    }

    @Test
    @Description("Отправка POST запроса и регистрация нового пользователя")
    @DisplayName("Проверка успешной регистрации нового пользователя")
    @Tag("ApiTests")
    @Step("Отправка запроса на регистрацию нового пользователя")
    void registerNewUserSuccess() {

        RegisterUserResponse response = given(CustomSpec.requestSpec)
                .body(TestData.validRegisterData)
                .when()
                .post(UserEndpoints.REGISTER_USER.getEndpoint())
                .then()
                .spec(responseSpec200)
                .extract().as(RegisterUserResponse.class);

        checkRegisterUserResponse(response);
    }

    @Step("Проверка успешной регистрации пользователя")
    private void checkRegisterUserResponse(RegisterUserResponse response) {
        assertThat(response.getId()).isNotNull();
        assertThat(response.getToken()).isNotNull();
        assertThat(response.getToken().length()).isGreaterThanOrEqualTo(17);
        assertThat(response.getToken()).matches("\\S[a-zA-Z0-9]*\\S");
    }

    @Test
    @Description("Отправка POST запроса и регистрация нового пользователя")
    @DisplayName("Проверка ошибки при попытке зарегистрировать пользователя без пароля")
    @Tag("ApiTests")
    @Step("Отправка запроса на регистрацию нового пользователя без пароля")
    void registerNewUserNegative() {

        ErrorResponse response = given(CustomSpec.requestSpec)
                .body(TestData.invalidRegisterData)
                .when()
                .post(UserEndpoints.REGISTER_USER.getEndpoint())
                .then()
                .spec(responseSpec400)
                .extract().as(ErrorResponse.class);

        checkErrorResponse(response);
    }

    @Step("Проверка ошибки в ответе при регистрации пользователя без пароля")
    private void checkErrorResponse(ErrorResponse response) {
        assertThat(response.getError()).isEqualTo(TestData.expectedErrorMessage);
    }

}