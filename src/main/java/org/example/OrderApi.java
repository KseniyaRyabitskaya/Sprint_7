package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {

    @Step("Send GET request to /api/v1/orders")
    public static Response getOrderList() {
        return given()
                .spec(RestAssuredUtils.getRequestSpecification())
                .and()
                .queryParam("limit", 10)
                .queryParam("page", 1)
                .when()
                .get("/api/v1/orders");
    }

    @Step("Send POST request to /api/v1/orders")
    public static Response createOrder(Order order) {
        return given()
                .spec(RestAssuredUtils.getRequestSpecification())
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Send PUT request to /api/v1/orders/cancel")
    public static Response cancelOrder(int track) {
        return given()
                .spec(RestAssuredUtils.getRequestSpecification())
                .and()
                .queryParam("track", track)
                .when()
                .put("/api/v1/orders/cancel");
    }
}
