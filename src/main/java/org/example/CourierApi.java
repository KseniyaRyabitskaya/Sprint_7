package org.example;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi {
    public static Response createCourier(Courier courier) {
        return given()
                .spec(RestAssuredUtils.getRequestSpecification())
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    public static Response loginCourier(CourierLogin courier) {
        return given()
                .spec(RestAssuredUtils.getRequestSpecification())
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }

    public static void deleteCourier(int id) {
        given()
                .spec(RestAssuredUtils.getRequestSpecification())
                .and()
                .when()
                .delete("/api/v1/courier/" + id);
    }
}
