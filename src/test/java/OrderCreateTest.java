import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    String firstName = "Testfirstname";
    String lastName = "Testlastname";
    String address = "Testaddress";
    int metroStation = 1;
    String phone = "+74951234567";
    int rentTime = 2;
    String deliveryDate = "2022-08-19T10:00:00.000Z";
    String comment = "Test comment";
    String[] color;

    public OrderCreateTest(String[] color) {
        this.color = color;
    }

    OrderActivities orderActivities = new OrderActivities();

    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK", "GRAY"}},
                {new String[]{}}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Create order test")
    @Description("Проверка создания заказа")
    public void createOrderTest() {
        orderActivities.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color)
                .assertThat()
                .body("track", notNullValue())
                .log()
                .all();

    }

    @After
    public void thisIsTheEnd() {
        orderActivities.canselOrder();
    }

}
