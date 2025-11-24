package tests;

import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class GetUserTests extends TestBase {

    @Test
    @Description("Отправка GET запроса и вывод список пользователей")
    @DisplayName("Проверка получения списка пользователей по номеру страницы")
    void getListUsersTestPositive() {
        given()
                .queryParam("page", 1)
                .when()
                .get("/users")
                .then()
                .log().all()
                .statusCode(200)
                .body("page", equalTo(1))
                .body("data.id", hasItem(6));

    }

    @Test
    @Description("Отправка GET запроса и вывод конкретного пользователя")
    @DisplayName("Получение конкретного пользователя по id")
    void getUserByIdTest() {
        given()
                .queryParam("id", 4)
                .when()
                .get("/users")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.id", equalTo(4))
                .body("data.email", equalTo("eve.holt@reqres.in"))
                .body("data.first_name", equalTo("Eve"))
                .body("data.last_name", equalTo("Holt"));

    }

    @Test
    @Description("Отправка GET запроса с id несуществующего пользователя")
    @DisplayName("Проверка корректной ошибки в ответе, если пользователь не найден")
    void getNotFoundUserTest() {
        given()
                .queryParam("id", 55)
                .log().uri()
                .when()
                .get("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

}