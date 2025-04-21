package org.example;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {
    public static Response getOrderList() {
        return given()
                .spec(RestAssuredUtils.getRequestSpecification())
                .and()
                .queryParam("limit", 10)
                .queryParam("page", 1)
                .when()
                .get("/api/v1/orders");
    }

    public static Response createOrder(Order order) {
        return given()
                .spec(RestAssuredUtils.getRequestSpecification())
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }
}
