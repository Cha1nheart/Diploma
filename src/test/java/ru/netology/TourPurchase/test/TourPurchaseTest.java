package ru.netology.TourPurchase.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import ru.netology.TourPurchase.data.CardInfo;
import ru.netology.TourPurchase.data.SQLhelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static ru.netology.TourPurchase.data.DataGenerator.*;
import static ru.netology.TourPurchase.page.TourPurchasePage.*;

class TourPurchaseTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @AfterEach
    void clearDataBaseTabs() {
        SQLhelper.MySQLcleanDataBase(); /* Для работы через MySQL. */
//        SQLhelper.PostgreSQLcleanDataBase(); /* Для работы через PostgreSQL. */
        /* При необходимости работы через PostgreSQL закомментируйте 33 строку и, соответственно, уберите комментарий с 34 строки кода. */
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

//    Обычные покупки тура.
//    Позитивные сценарии проверки.

    @Test
    @DisplayName("Should successfully approve operation for the first card") /* Обычная проверка со стандартными данными ввода. */
    void shouldSuccessfullyApproveOperationFirstCard() { /* Проверка уведомления и внесения данных в БД. */
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear); /* Переменная "validYear" сохраняет результат вызова метода "generateYear()". Она понадобится для метода "generateMonth()". */
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        approvedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
        var purchaseStatus = SQLhelper.getStatusFromPaymentMySQL();
        Assertions.assertEquals("APPROVED", purchaseStatus);
    }

    @Test
    @DisplayName("Should successfully approve operation for the first card(PostgreSQL)") /* Обычная проверка со стандартными данными ввода. */
    void shouldSuccessfullyApproveOperationFirstCardPostgreSQL() { /* Проверка уведомления и внесения данных в БД. */
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        approvedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
        var purchaseStatus = SQLhelper.getStatusFromPaymentPostgreSQL();
        Assertions.assertEquals("APPROVED", purchaseStatus);
    }

    @Test
    @DisplayName("Should decline invalid format for the card number field") /* Поле "Карта" не заполнено полностью, проверка уведомления. */
    void shouldDeclineInvalidCardFormat() {
        tourPurchaseButton.click();
        cardNumberField.setValue(generateInvalidCardNumberFormat());
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        wrongFormatNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the month field") /* Поле "Месяц" заполнено вне формата(две цифры), проверка уведомления. */
    void shouldDeclineInvalidMonthFormat() {
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateInvalidMonthOrYearFormat());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        wrongFormatNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the year field") /* Поле "Год" заполнено вне формата(две цифры), проверка уведомления. */
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
    @DisplayName("Should decline invalid format for the CVC/CVV code field") /* Поле "CVC/CVV" не заполнено полностью, проверка уведомления. */
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
    @DisplayName("Should decline invalid card") /* Неприемлемые номера карты - банк отказывает в проведении операции, проверка уведомления. */
    void shouldDeclineInvalidCard() {
        tourPurchaseButton.click();
        cardNumberField.setValue(generateInvalidCardNumber());
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        declinedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid month") /* Срок действия карты истек - прошедший месяц, проверка уведомления. */
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
    @DisplayName("Should decline because of the year is passed") /* Срок действия карты истек - прошедший год, проверка уведомления. */
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
    @DisplayName("Should decline operation because of unreal expiration year") /* Истекший срок действия карты - год, проверка уведомления. */
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

//    Негативные сценарии проверки.

    @Test
    @DisplayName("Should decline operation for the second card") /* Банк должен отклонить операцию второй карты. */
    void shouldSuccessfullyApproveOperationForSecondCard() { /* Проверка уведомления и внесения данных в БД. */
        tourPurchaseButton.click();
        cardNumberField.setValue(secondCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        declinedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
        var purchaseStatus = SQLhelper.getStatusFromPaymentMySQL();
        Assertions.assertEquals("APPROVED", purchaseStatus);
    }

    @Test
    @DisplayName("Should get the same amount of purchase for the first card in DB") /* Сумма покупки тура должна совпадать с числом "45000". */
    void shouldGetSameAmountOfPurchaseFromDB() { /* Проверка внесения данных в БД. */
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        var purchaseAmount = SQLhelper.getAmountFromPaymentMySQL();
        Assertions.assertEquals(45000, purchaseAmount);
    }

    @Test
    @DisplayName("Should decline invalid input in owner field ") /* Невалидные данные в поле "Владелец"(цифры, символы), проверка уведомления. */
    void shouldDeclineInvalidInputOwner() {
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateInvalidHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        declinedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid input as 00 for the month field") /* Поле "Месяц" заполнено нулями, проверка уведомления. */
    void shouldDeclineInvalidMonthInput() {
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(zeroDigitsAsMonth);
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        wrongFormatNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

//    Две негативных проверки через подключение к PostgreSQL.

    @Test
    @DisplayName("Should decline operation for the second card(PostgreSQL)") /* Банк должен отклонить операцию второй карты. */
    void shouldSuccessfullyApproveOperationForSecondCardPostgreSQL() { /* Проверка уведомления и внесения данных в БД. */
        tourPurchaseButton.click();
        cardNumberField.setValue(secondCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        declinedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
        var purchaseStatus = SQLhelper.getStatusFromPaymentPostgreSQL();
        Assertions.assertEquals("APPROVED", purchaseStatus);
    }

    @Test
    @DisplayName("Should get the same amount of purchase for the first card in DB(PostgreSQL)") /* Сумма покупки тура должна совпадать с числом "45000". */
    void shouldGetSameAmountOfPurchaseFromDBPostgreSQL() { /* Проверка внесения данных в БД. */
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        var purchaseAmount = SQLhelper.getAmountFromPaymentPostgreSQL();
        Assertions.assertEquals(45000, purchaseAmount);
    }

//    Проверки API.
//    Позитивные сценарии.

    @Test
    @DisplayName("First card is accepted by data base response")
    void shouldAcceptFirstCardByDB() { /* Позитивная проверка для первой карты, номер - "4444 4444 4444 4441". */

        CardInfo cardInfo = new CardInfo(
                firstCardNumber,
                validYear,
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
                validYear,
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
                validYear,
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