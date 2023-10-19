package ru.netology.TourPurchase.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import ru.netology.TourPurchase.data.CardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static ru.netology.TourPurchase.data.DataGenerator.*;
import static ru.netology.TourPurchase.page.TourPurchasePage.*;

class TourPurchaseTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

//    Обычная покупка тура.
//    Позитивный сценарий проверки.

    @Test
    @DisplayName("Should successfully approve operation for the first card") /* Обычная проверка со стандартными данными ввода. */
    void shouldSuccessfullyApproveOperationFirstCard() {
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(generateYear());
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        approvedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

//    Негативные сценарии проверки.

    @Test
    @DisplayName("Should decline operation for the second card") /* Банк должен отклонить операцию второй карты. */
    void shouldSuccessfullyApproveOperationForSecondCard() {     /* Ответ БД - отказ, но уведомление сообщает об успехе проведенной операции. */
        tourPurchaseButton.click();
        cardNumberField.setValue(secondCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(generateYear());
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        declinedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the card number field") /* Поле "Карта" не заполнено полностью. */
    void shouldDeclineInvalidCardFormat() {
        tourPurchaseButton.click();
        cardNumberField.setValue(generateInvalidCardNumberFormat());
        monthField.setValue(generateMonth());
        yearField.setValue(generateYear());
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        wrongFormatNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the month field") /* Поле "Месяц" заполнено вне формата(две цифры). */
    void shouldDeclineInvalidMonthFormat() {
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateInvalidMonthOrYearFormat());
        yearField.setValue(generateYear());
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        wrongFormatNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid input as 00 for the month field") /* Поле "Месяц" заполнено нулями. */
    void shouldDeclineInvalidMonthInput() {
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(zeroDigitsAsMonth);
        yearField.setValue(generateYear());
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        wrongFormatNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the year field") /* Поле "Год" заполнено вне формата(две цифры). */
    void shouldDeclineInvalidYearFormat() {
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(generateInvalidMonthOrYearFormat());
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        wrongFormatNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the CVC/CVV code field") /* Поле "CVC/CVV" не заполнено полностью. */
    void shouldDeclineInvalidCvcFormat() {
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateInvalidCvcFormat());
        continueButton.click();
        wrongFormatNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid card") /* Неприемлемые номера карты - банк отказывает в проведении операции. */
    void shouldDeclineInvalidCard() {
        tourPurchaseButton.click();
        cardNumberField.setValue(generateInvalidCardNumber());
        monthField.setValue(generateMonth());
        yearField.setValue(generateYear());
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        declinedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid month") /* Срок действия карты истек - прошедший месяц. */
    void shouldDeclineInvalidMonth() {
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateInvalidMonth());
        yearField.setValue(currentYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        invalidCardExpirationDateNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline because of the year is passed") /* Срок действия карты истек - прошедший год. */
    void shouldDeclineInvalidYearPassed() {
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(generateInvalidYearPassed());
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        expiredCardNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline unreal because of unreal expiration year") /* Истекший срок действия карты - год. */
    void shouldDeclineInvalidYearExpired() {
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(generateInvalidYearExpired());
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        invalidCardExpirationDateNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid input in owner field ") /* Невалидные данные в поле "Владелец"(цифры, символы). */
    void shouldDeclineInvalidInputOwner() {
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(generateYear());
        holderField.setValue(generateInvalidHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        declinedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

//    Сценарии проверки ответов базы данных.
//    Позитивные сценарии.

    @Test
    @DisplayName("First card is accepted by data base response")
    void shouldAcceptFirstCardByDB() { /* Позитивная проверка для первой карты, номер - "4444 4444 4444 4441". */

        CardInfo cardInfo = new CardInfo(
                firstCardNumber,
                generateYear(),
                generateMonth(),
                generateHolder(),
                generateCVC()
        );

        given().
                baseUri("http://localhost:8080/api/v1").
                contentType(ContentType.JSON).
                body(cardInfo).
        when().
                post("/pay").
        then().
                statusCode(equalTo(200)).
                body("status", equalTo("APPROVED"));
    }

    @Test
    @DisplayName("Second card is declined by data base response")
    void shouldDeclineSecondCardByDB() { /* Позитивная проверка для второй карты, номер - "4444 4444 4444 4442". */

        CardInfo cardInfo = new CardInfo(
                secondCardNumber,
                generateYear(),
                generateMonth(),
                generateHolder(),
                generateCVC()
        );

        given().
                baseUri("http://localhost:8080/api/v1").
                contentType(ContentType.JSON).
                body(cardInfo).
        when().
                post("/pay").
        then().
                statusCode(equalTo(200)).
                body("status", equalTo("DECLINED"));
    }

//    Негативный сценарий.

    @Test
    @DisplayName("Data Base will fail with any card numbers other than 4444 4444 4444 4441 or 4444 4444 4444 4442")
    void shouldFailWith() { /* База данных обрабатывает только два номера карт: 4444 4444 4444 4441 или 4444 4444 4444 4442. */
                            /* Другие значения для поля "Номер карты" всегда дают этот результат. */

        CardInfo cardInfo = new CardInfo(
                generateInvalidCardNumber(),
                generateYear(),
                generateMonth(),
                generateHolder(),
                generateCVC()
        );

        given().
                baseUri("http://localhost:8080/api/v1").
                contentType(ContentType.JSON).
                body(cardInfo).
        when().
                post("/pay").
        then().
                statusCode(equalTo(500)).
                body(
                "error", equalTo("Internal Server Error"),
                "message", equalTo("400 Bad Request"),
                "path", equalTo("/api/v1/pay"));
    }

}