import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierActivities {
    @Step("Courier create")
    public ValidatableResponse create(String login, String password, String firstName) throws NullPointerException{
        Gson gson = new Gson();
        Courier courier = new Courier(login, password, firstName);
        String json = gson.toJson(courier);
        System.out.println(json);
        ValidatableResponse responseCreate =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .when()
                        .post("/api/v1/courier")
                        .then();
        return responseCreate;
    }

    @Step("Courier login")
    public ValidatableResponse login(String login, String password) {
        String json = "{\"login\": \"" + login + "\", \"password\": \"" + password + "\"}";
        ValidatableResponse responseLogin =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .post("/api/v1/courier/login")
                        .then();
        return responseLogin;
    }

    @Step("Courier delete")
    public void delete(int courierId) {
        String hand = "/api/v1/courier/" + courierId;
        String json = "{\"id\": " + courierId + "}";
        given()
                .header("Content-type", "application/json")
                .body(json)
                .delete(hand);
    }
}
