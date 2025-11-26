package tests;

import endpoints.UserEndpoints;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import models.ErrorResponse;
import models.User;
import models.UserListResponse;
import models.UserSingleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.CustomSpec;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.CustomSpec.responseSpec200;
import static specs.CustomSpec.responseSpec404;

public class GetUserTests extends TestBase {


    @Test
    @Description("Отправка GET запроса и вывод списка пользователей")
    @DisplayName("Проверка получения списка пользователей по номеру страницы")
    @Step("Запрос списка пользователей по странице")
    void getListUsersTestPositive() {
        UserListResponse response = given(CustomSpec.requestSpec)
                .queryParam("page", 1)
                .when()
                .get(UserEndpoints.LIST_USERS.getEndpoint())
                .then()
                .statusCode(200)
                .spec(responseSpec200)
                .extract().as(UserListResponse.class);

        checkListUsers(response);
    }

    @Step("Проверка отображения списка пользователей и всех параметров")
    private void checkListUsers(UserListResponse response) {
        assertThat(response.getPage()).isEqualTo(1);
        assertThat(response.getData()).hasSize(6);
    }


    @Test
    @Description("Отправка GET запроса и вывод конкретного пользователя")
    @DisplayName("Получение конкретного пользователя по id")
    @Step("Запрос пользователя по id")
    void getUserByIdTest() {
        UserSingleResponse response = given(CustomSpec.requestSpec)
                .pathParam("id", 4)
                .when()
                .get(UserEndpoints.SINGLE_USER.getEndpoint())
                .then()
                .spec(responseSpec200)
                .extract().as(UserSingleResponse.class);

        checkUserById(response);
    }

    @Step("Проверка отображения конкретного пользователя и всех параметров")
    private void checkUserById(UserSingleResponse response) {
        User user = response.getData();
        assertThat(user.getId()).isEqualTo(4);
        assertThat(user.getEmail()).isEqualTo("eve.holt@reqres.in");
        assertThat(user.getFirstName()).isEqualTo("Eve");
        assertThat(user.getLastName()).isEqualTo("Holt");
    }

    @Test
    @Description("Отправка GET запроса с id несуществующего пользователя")
    @DisplayName("Проверка корректной ошибки в ответе, если пользователь не найден")
    @Step("Запрос пользователя по несуществующему id")
    void getNotFoundUserTest() {
        ErrorResponse response = given(CustomSpec.requestSpec)
                .pathParam("id", 55)
                .log().uri()
                .when()
                .get(UserEndpoints.SINGLE_USER.getEndpoint())
                .then()
                .spec(responseSpec404)
                .extract().as(ErrorResponse.class);

        checkErrorResponse(response);
    }

    @Step("Проверка отображения ошибки 404")
    private void checkErrorResponse(ErrorResponse response) {
        assertThat(response.getError()).isEqualTo(null);
    }
}