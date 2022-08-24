import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateTest {

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
    @DisplayName("Courier create test")
    @Description("Создание курьера")
    public void courierCreateTest() {
        courierActivities.create(login, password, firstName)
                .assertThat()
                .body("ok", equalTo(true))
                .statusCode(201);
    }

    @Test
    @DisplayName("Courier create double test")
    @Description("Попытка создания двух одинаковых курьеров")
    public void courierCreateDoubleTest() {
        courierActivities.create(login, password, firstName);
        courierActivities.create(login, password, firstName)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .statusCode(409);
    }

    @Test
    @DisplayName("Courier create missed login test")
    @Description("Попытка создания курьера без логина")
    public void courierCreateMissedLoginTest() {
        String login = "";
        courierActivities.create(login, password, firstName)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Courier create missed password test")
    @Description("Попытка создания курьера без пароля")
    public void courierCreateMissedPasswordTest() {
        String password = "";
        courierActivities.create(login, password, firstName)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @After
    public void thisIsTheEnd() throws NullPointerException{
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
