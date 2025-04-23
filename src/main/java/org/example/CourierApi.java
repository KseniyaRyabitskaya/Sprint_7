package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi {

    @Step("Send POST request to /api/v1/courier")
    public static Response createCourier(Courier courier) {
        return given()
                .spec(RestAssuredUtils.getRequestSpecification())
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

    }

    @Step("Send POST request to /api/v1/courier/login")
    public static Response loginCourier(CourierLogin courier) {
        return given()
                .spec(RestAssuredUtils.getRequestSpecification())
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Send DELETE request to /api/v1/courier/")
    public static Response deleteCourier(int id) {
        return given()
                .spec(RestAssuredUtils.getRequestSpecification())
                .and()
                .when()
                .delete("/api/v1/courier/" + id);
    }
}
