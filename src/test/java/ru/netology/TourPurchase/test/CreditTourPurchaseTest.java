package ru.netology.TourPurchase.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.TourPurchase.data.DataGenerator.*;
import static ru.netology.TourPurchase.data.DataGenerator.declinedByTheBankNotification;
import static ru.netology.TourPurchase.page.CreditTourPurchasePage.creditTourPurchaseButton;

public class CreditTourPurchaseTest {

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

//    Позитивные сценарии проверки для покупки тура в кредит.

    @Test
    @DisplayName("Should successfully approve credit purchase for the first card") /* Первая карта. */
    void shouldSuccessfullyApproveCreditPurchaseFirstCard() {
        creditTourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        approvedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should successfully approve credit purchase operation for the second card") /* Вторая карта. */
    void shouldSuccessfullyApproveCreditPurchaseSecondCard() {
        creditTourPurchaseButton.click();
        cardNumberField.setValue(secondCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        approvedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

//    Негативные сценарии проверки для покупки тура в кредит.

    @Test
    @DisplayName("Should decline invalid format for the card number field, credit purchase") /* Поле "Карта" не заполнено полностью. */
    void shouldDeclineInvalidCardFormatCreditPurchase() {
        creditTourPurchaseButton.click();
        cardNumberField.setValue(generateInvalidCardNumberFormat());
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        wrongFormatNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the month field, credit purchase") /* Поле "Месяц" заполнено вне формата(две цифры). */
    void shouldDeclineInvalidMonthFormatCreditPurchase() {
        creditTourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateInvalidMonthOrYearFormat());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        wrongFormatNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the year field, credit purchase") /* Поле "Год" заполнено вне формата(две цифры). */
    void shouldDeclineInvalidYearFormatCreditPurchase() {
        creditTourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(generateInvalidMonthOrYearFormat());
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        wrongFormatNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the CVC/CVV code field, credit purchase") /* Поле "CVC/CVV" не заполнено полностью. */
    void shouldDeclineInvalidCvcFormatCreditPurchase() {
        creditTourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateInvalidCvcFormat());
        continueButton.click();
        wrongFormatNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid card, credit purchase") /* Неприемлемые номера карты - банк отказывает в проведении операции. */
    void shouldDeclineInvalidCardCreditPurchase() {
        creditTourPurchaseButton.click();
        cardNumberField.setValue(generateInvalidCardNumber());
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        declinedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid month, credit purchase") /* Срок действия карты истек - прошедший месяц. */
    void shouldDeclineInvalidMonthCreditPurchase() {
        creditTourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateInvalidMonth());
        yearField.setValue(currentYear);
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        invalidCardExpirationDateNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline because of the year is passed, credit purchase") /* Срок действия карты истек - прошедший год. */
    void shouldDeclineInvalidYearPassedCreditPurchase() {
        creditTourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(generateInvalidYearPassed());
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        expiredCardNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline unreal because of unreal expiration year, credit purchase") /* Недействительный срок действия карты - год. */
    void shouldDeclineInvalidYearExpiredCreditPurchase() {
        creditTourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(generateInvalidYearExpired());
        holderField.setValue(generateHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        invalidCardExpirationDateNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid input in owner field, credit purchase") /* Невалидные данные в поле "Владелец"(цифры, символы). */
    void shouldDeclineInvalidInputOwnerCreditPurchase() {                        /* Тест будет падать, так как поле "Владелец" принимает все. */
        creditTourPurchaseButton.click();
        cardNumberField.setValue(firstCardNumber);
        monthField.setValue(generateMonth());
        yearField.setValue(validYear);
        holderField.setValue(generateInvalidHolder());
        cvcField.setValue(generateCVC());
        continueButton.click();
        declinedByTheBankNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }
    
}
