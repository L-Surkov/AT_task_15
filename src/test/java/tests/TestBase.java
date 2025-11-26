package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    public static final Header header = new Header("x-api-key", "reqres-free-v1");
    @BeforeAll
    public static void setupEnvironment() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";

    }
}