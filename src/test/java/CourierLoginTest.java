import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {
    private CourierActivities courierActivities = new CourierActivities();
    String login = "Testlogin";
    String password = "Testpassword";
    String firstName = "Testfirstname";
    int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("Courier login successful test")
    @Description("Успешная авторизация курьера")
    public void courierLoginSuccessfulTest() {
        courierActivities.create(login, password, firstName);
        courierActivities.login(login, password)
                .assertThat()
                .body("id", notNullValue())
                .statusCode(200);
    }

    @Test
    @DisplayName("Courier login wrong login test")
    @Description("Попытка авторизации курьера с неправильным логином")
    public void courierLoginWrongLoginTest() {
        courierActivities.create(login, password, firstName);
        courierActivities.login("Testwronglogin", password)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }

    @Test
    @DisplayName("Courier login wrong password test")
    @Description("Попытка авторизации курьера с неправильным паролем")
    public void courierLoginWrongPasswordTest() {
        courierActivities.create(login, password, firstName);
        courierActivities.login(login, "Testwrongpassword")
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }

    @Test
    @DisplayName("Courier login empty login test")
    @Description("Попытка авторизации курьера с пустым логином")
    public void courierLoginEmptyLoginTest() {
        courierActivities.create(login, password, firstName);
        courierActivities.login("", password)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Courier login empty password test")
    @Description("Попытка авторизации курьера с пустым паролем")
    public void courierLoginEmptyPasswordTest() {
        courierActivities.create(login, password, firstName);
        courierActivities.login(login, "")
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);

    }

    @After
    public void thisIsTheEnd() {
        try {
            courierId = courierActivities.login(login, password)
                    .extract()
                    .path("id");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (courierId != 0) {
            courierActivities.delete(courierId);
        }
    }
}
