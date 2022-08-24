import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderActivities {
    ValidatableResponse response;
    @Step("Order create")
    public ValidatableResponse createOrder(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        response = given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
        return response;
    }

    @Step("Order cancel")
    public void canselOrder() {
        int track = response
                .extract()
                .path("track");
        String json = "{\"track\": \"" + track + "\"}";
        if(track != 0) {
            given()
                    .header("Content-type", "application/json")
                    .body(json)
                    .when()
                    .put("/api/v1/orders/cancel");
        }
    }
}
