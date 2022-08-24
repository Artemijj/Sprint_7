import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class OrderListGetActivities {
    @Step("Order accept")
    public void orderAccept(int orderId, int courierId) {
        String hand = "/api/v1/orders/accept/" + orderId + "?courierId=" + courierId;
        given()
                .header("Content-type", "application/json")
                .put(hand);
    }
}
