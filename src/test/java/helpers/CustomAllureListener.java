package helpers;

import io.qameta.allure.restassured.AllureRestAssured;

public class CustomAllureListener {
    private static final AllureRestAssured FILTER = new AllureRestAssured();

    public static AllureRestAssured withCustomTemplates() {
        FILTER.setRequestTemplate("src/test/java/resources/tpl/request.ftl");
        FILTER.setResponseTemplate("src/test/java/resources/tpl/response.ftl");
        return FILTER;
    }
}