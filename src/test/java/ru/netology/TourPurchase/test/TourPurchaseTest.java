package ru.netology.TourPurchase.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static ru.netology.TourPurchase.data.DataGenerator.*;

class TourPurchaseTest {

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

//    Позитивные проверки для валидных карт(первая - "4444444444444441", вторая - "4444444444444442").

//    Обычные покупки тура.

    @Test
    @DisplayName("Should successfully approve operation for the first card") /* Первая карта. */
    void shouldSuccessfullyApproveOperationFirstCard() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'notification__content\']")
                .findBy(Condition.exactText("Операция одобрена Банком."))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should successfully approve operation for the second card") /* Вторая карта. */
    void shouldSuccessfullyApproveOperationForSecondCard() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(secondCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'notification__content\']")
                .findBy(Condition.exactText("Операция одобрена Банком."))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

//    Покупка тура в кредит

    @Test
    @DisplayName("Should successfully approve credit purchase for the first card") /* Первая карта. */
    void shouldSuccessfullyApproveCreditPurchaseFirstCard() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'notification__content\']")
                .findBy(Condition.exactText("Операция одобрена Банком."))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should successfully approve credit purchase operation for the second card") /* Вторая карта. */
    void shouldSuccessfullyApproveCreditPurchaseSecondCard() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(secondCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'notification__content\']")
                .findBy(Condition.exactText("Операция одобрена Банком."))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

//    Негативные сценарии.

//    Обычные покупка тура.

    @Test
    @DisplayName("Should decline invalid format for the card number field") /* Поле "Карта" не заполнено полностью. */
    void shouldDeclineInvalidCardFormat() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(generateInvalidCardNumberFormat());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Неверный формат"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the month field") /* Поле "Месяц" заполнено вне формата(две цифры). */
    void shouldDeclineInvalidMonthFormat() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateInvalidMonthOrYearFormat());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Неверный формат"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the year field") /* Поле "Год" заполнено вне формата(две цифры). */
    void shouldDeclineInvalidYearFormat() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(generateInvalidMonthOrYearFormat());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Неверный формат"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the CVC/CVV code field") /* Поле "CVC/CVV" не заполнено полностью. */
    void shouldDeclineInvalidCvcFormat() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateInvalidCvcFormat());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Неверный формат"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid card") /* Неприемлемые номера карты - банк отказывает в проведении операции. */
    void shouldDeclineInvalidCard() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(generateInvalidCardNumber());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'notification__content\']")
                .findBy(Condition.exactText("Ошибка! Банк отказал в проведении операции."))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid month") /* Срок действия карты истек - прошедший месяц. */
    void shouldDeclineInvalidMonth() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateInvalidMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(currentYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Неверно указан срок действия карты"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline because of the year is passed") /* Срок действия карты истек - прошедший год. */
    void shouldDeclineInvalidYearPassed() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(generateInvalidYearPassed());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Истёк срок действия карты"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline unreal because of unreal expiration year") /* Недействительный срок действия карты - год. */
    void shouldDeclineInvalidYearExpired() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(generateInvalidYearExpired());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Неверно указан срок действия карты"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid input in owner field ") /* Невалидные данные в поле "Владелец"(цифры, символы). */
    void shouldDeclineInvalidInputOwner() {                      /* Тест будет падать, так как поле "Владелец" принимает все. */
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateInvalidHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Ошибка! Банк отказал в проведении операции.")) /* Сообщения о неверном владельце нет. */
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

//    Негативные проверки покупки тура в кредит

    @Test
    @DisplayName("Should decline invalid format for the card number field, credit purchase") /* Поле "Карта" не заполнено полностью. */
    void shouldDeclineInvalidCardFormatCreditPurchase() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить в кредит")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(generateInvalidCardNumberFormat());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Неверный формат"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the month field, credit purchase") /* Поле "Месяц" заполнено вне формата(две цифры). */
    void shouldDeclineInvalidMonthFormatCreditPurchase() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить в кредит")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateInvalidMonthOrYearFormat());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Неверный формат"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the year field, credit purchase") /* Поле "Год" заполнено вне формата(две цифры). */
    void shouldDeclineInvalidYearFormatCreditPurchase() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить в кредит")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(generateInvalidMonthOrYearFormat());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Неверный формат"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid format for the CVC/CVV code field, credit purchase") /* Поле "CVC/CVV" не заполнено полностью. */
    void shouldDeclineInvalidCvcFormatCreditPurchase() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить в кредит")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateInvalidCvcFormat());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Неверный формат"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid card, credit purchase") /* Неприемлемые номера карты - банк отказывает в проведении операции. */
    void shouldDeclineInvalidCardCreditPurchase() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить в кредит")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(generateInvalidCardNumber());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'notification__content\']")
                .findBy(Condition.exactText("Ошибка! Банк отказал в проведении операции."))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid month, credit purchase") /* Срок действия карты истек - прошедший месяц. */
    void shouldDeclineInvalidMonthCreditPurchase() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить в кредит")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateInvalidMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(currentYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Неверно указан срок действия карты"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline because of the year is passed, credit purchase") /* Срок действия карты истек - прошедший год. */
    void shouldDeclineInvalidYearPassedCreditPurchase() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить в кредит")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(generateInvalidYearPassed());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Истёк срок действия карты"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline unreal because of unreal expiration year, credit purchase") /* Недействительный срок действия карты - год. */
    void shouldDeclineInvalidYearExpiredCreditPurchase() {
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить в кредит")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(generateInvalidYearExpired());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Неверно указан срок действия карты"))
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    @DisplayName("Should decline invalid input in owner field, credit purchase") /* Невалидные данные в поле "Владелец"(цифры, символы). */
    void shouldDeclineInvalidInputOwnerCreditPurchase() {                        /* Тест будет падать, так как поле "Владелец" принимает все. */
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить в кредит")).click();
        $$("[class=\'input__inner\']")
                .findBy(Condition.exactText("Номер карты"))
                .find("[class=\'input__control\']").setValue(firstCardNumber);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Месяц"))
                .find("[class=\'input__control\']").setValue(generateMonth());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Год"))
                .find("[class=\'input__control\']").setValue(validYear);
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("Владелец"))
                .find("[class=\'input__control\']").setValue(generateInvalidHolder());
        $$("[class=\'input-group__input-case\']")
                .findBy(Condition.exactText("CVC/CVV"))
                .find("[class=\'input__control\']").setValue(generateCVC());
        $$("[role=\'button\']")
                .findBy(Condition.exactText("Продолжить")).click();
        $$("[class=\'input__sub\']")
                .findBy(Condition.exactText("Ошибка! Банк отказал в проведении операции.")) /* Сообщения о неверном владельце нет. */
                .shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

}