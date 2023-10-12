package ru.netology.TourPurchase.test;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DataBaseTest {

    @Test
    void shouldReturnTheSame() {
        given().
            baseUri("http://localhost:8080/api/v1/pay").
            body("some data").
        when().
            post("/post").
        then().
            statusCode(200).
            body("data", equalTo("some data"))
        ;
    }

}
