package ru.netology.TourPurchase.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
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

//    Обычные покупки тура.
//    Позитивные сценарии проверки для валидных карт(первая - "4444444444444441", вторая - "4444444444444442").

    @Test
    @DisplayName("Should successfully approve operation for the first card") /* Первая карта. */
    void shouldSuccessfullyApproveOperationFirstCard() {
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        approvedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should successfully approve operation for the second card") /* Вторая карта. */
    void shouldSuccessfullyApproveOperationForSecondCard() {
        tourPurchaseButton.click();
        cardNumberField.setValue(secondCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        approvedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

//    Негативные сценарии проверки для обычной покупки тура.

    @Test
    @DisplayName("Should decline invalid format for the card number field") /* Поле "Карта" не заполнено полностью. */
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
    @DisplayName("Should decline invalid format for the month field") /* Поле "Месяц" заполнено вне формата(две цифры). */
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
        yearField.setValue(validYear);
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
    @DisplayName("Should decline unreal because of unreal expiration year") /* Недействительный срок действия карты - год. */
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
    void shouldDeclineInvalidInputOwner() {                      /* Тест будет падать, так как поле "Владелец" принимает все. */
        tourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateInvalidHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        declinedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

}