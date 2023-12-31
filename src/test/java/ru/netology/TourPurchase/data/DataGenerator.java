package ru.netology.TourPurchase.data;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$$;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("ru_RU"));

//    Переменные для удобства чтения тестов: xpath пути к кнопкам, полям и уведомлениям.

    public static SelenideElement cardNumberField = $$("[class=\'input__inner\']")
            .findBy(Condition.exactText("Номер карты"))
            .find("[class=\'input__control\']");
    public static SelenideElement monthField = $$("[class=\'input-group__input-case\']")
            .findBy(Condition.exactText("Месяц"))
            .find("[class=\'input__control\']");
    public static SelenideElement yearField = $$("[class=\'input-group__input-case\']")
            .findBy(Condition.exactText("Год"))
            .find("[class=\'input__control\']");
    public static SelenideElement holderField = $$("[class=\'input-group__input-case\']")
            .findBy(Condition.exactText("Владелец"))
            .find("[class=\'input__control\']");
    public static SelenideElement cvcField = $$("[class=\'input-group__input-case\']")
            .findBy(Condition.exactText("CVC/CVV"))
            .find("[class=\'input__control\']");
    public static SelenideElement continueButton = $$("[role=\'button\']")
            .findBy(Condition.exactText("Продолжить"));
    public static SelenideElement approvedByTheBankNotification = $$("[class=\'notification__content\']")
            .findBy(Condition.exactText("Операция одобрена Банком."));
    public static SelenideElement declinedByTheBankNotification = $$("[class=\'notification__content\']")
            .findBy(Condition.exactText("Ошибка! Банк отказал в проведении операции."));
    public static SelenideElement wrongFormatNotification = $$("[class=\'input__sub\']")
            .findBy(Condition.exactText("Неверный формат"));
    public static SelenideElement invalidCardExpirationDateNotification = $$("[class=\'input__sub\']")
            .findBy(Condition.exactText("Неверно указан срок действия карты"));
    public static SelenideElement expiredCardNotification = $$("[class=\'input__sub\']")
            .findBy(Condition.exactText("Истёк срок действия карты"));

//    Переменные для работы тестов и дата класса.

    public static String firstCardNumber = "4444 4444 4444 4441";
    public static String secondCardNumber = "4444 4444 4444 4442";
    public static String currentYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    public static String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    public static String zeroDigitsAsMonth = "00";
    public static int currentMonthInt = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
    public static int currentYearInt = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yy")));

//    Методы дата класса: возвращают различные значения для полей.

    public static String generateInvalidCardNumberFormat() { /* Возвращает 15 случайных цифр. */
        String invalidCardNumberFormat = faker.bothify("#### #### #### ###");
        return invalidCardNumberFormat; /* Невалидное значение. */
    }

    public static String generateInvalidCardNumber() { /* Возвращает 16 случайных цифр, кроме значений первой и второй карты. */
        String invalidCardNumber = faker.bothify("#### #### #### ####");
        for (int i = 0; invalidCardNumber == firstCardNumber || invalidCardNumber == secondCardNumber; i++) {
            invalidCardNumber = faker.bothify("#### #### #### ####");
        }
        return invalidCardNumber; /* Невалидное значение. */
    }

    public static String generateYear() { /* Возвращает валидные значения для года согласно сроку действия дебетовых карт(до 5 лет). */
        String validYear;
        int currentYearPlusExpire = currentYearInt + 4; /* Суммирует текущую дату года со сроками действия карт. */
        int validYearInt = faker.number().numberBetween(currentYearInt, currentYearPlusExpire); /* Диапазон чисел между датой текущего года и на 4 года вперед. */
        if (validYearInt <= 9) {
            validYear = Integer.toString(validYearInt);
            validYear = "0" + validYear; /* Правка для правильного формата ввода в поле "Год". */
        } else {
            validYear = Integer.toString(validYearInt);
        }
        return validYear;
    }

    public static String validYear = generateYear();
    /* Переменная нужна для сохранения результата вызова метода. Она понадобится для метода "generateMonth()". */

    public static String generateInvalidMonthOrYearFormat() { /* Возвращает одну из двух цифр(0, 9). */
        String invalidYearFormat = String.valueOf(faker.number().numberBetween(0, 9));
        return invalidYearFormat; /* Невалидное значение. */
    }

    public static String generateInvalidYearPassed() { /* Возвращает случайный прошедший год согласно текущей дате. */
        String invalidYearPassed;
        if (currentYearInt >= 1) { /* Нельзя использовать отрицательные числа. */
            int invalidYearPassedMinusOneInt = currentYearInt - 1; /* Прошедший год. */
            int invalidYearPassedInt = faker.number().numberBetween(0, invalidYearPassedMinusOneInt); /* Генерация диапазона чисел согласно текущей дате. */
            if (invalidYearPassedInt <= 9) {
                invalidYearPassed = Integer.toString(invalidYearPassedInt);
                invalidYearPassed = "0" + invalidYearPassed; /* Правка для правильного формата ввода в поле "Год". */
            } else {
                invalidYearPassed = Integer.toString(invalidYearPassedInt);
            }
        } else {
            int invalidYearPassedInt = 99; /* Логика метода генерирует числа от "0-98" в зависимости от текущей даты, поэтому остается только число "99". */
            invalidYearPassed = Integer.toString(invalidYearPassedInt);
        }
        return invalidYearPassed; /* Невалидное значение. */
    }

    public static String generateInvalidYearExpired() { /* Возвращает год с нереальным сроком действия дебетовой карты согласно текущей дате. */
        String invalidYearExpired;
        if (currentYearInt <= 93) {
            int currentYearPlusExpire = currentYearInt + 5; /* Просроченная карта с расчетом срока действия от актуальной даты. */
            int invalidYearExpiredInt = faker.number().numberBetween(currentYearPlusExpire, 99); /* Генерация диапазона чисел даты для просроченных карт. */
            if (invalidYearExpiredInt <= 9) {
                invalidYearExpired = Integer.toString(invalidYearExpiredInt);
                invalidYearExpired = "0" + invalidYearExpired; /* Правка для правильного формата ввода в поле "Год". */
            } else {
                invalidYearExpired = Integer.toString(invalidYearExpiredInt);
            }
        } else {
            invalidYearExpired = "00"; /* Логика метода генерирует числа от "1-99" в зависимости от текущего года, поэтому остается только число "00". */
        }
        return invalidYearExpired; /* Невалидное значение. */
    }

    public static String generateMonth() { /* Возвращает случайный месяц согласно текущей дате. */
        String validMonth;
        if (Integer.parseInt(validYear) > currentYearInt) { /* Если сгенерированная дата года получилась в будущем. */
            int validMonthInt = faker.number().numberBetween(1, 12); /* Диапазон для поля "Месяц" равен "1" и "12". */
            if (validMonthInt <= 9) {
                validMonth = Integer.toString(validMonthInt);
                validMonth = "0" + validMonth; /* Правка для правильного формата ввода в поле "Месяц". */
            } else {
                validMonth = Integer.toString(validMonthInt);
            }
        } else {
            int validMonthInt = faker.number().numberBetween(currentMonthInt, 12); /* Если сгенерированная дата года совпала с текущей: диапазон чисел будет генерироваться от текущего месяца до "12". */
            if (validMonthInt <= 9) {
                validMonth = Integer.toString(validMonthInt);
                validMonth = "0" + validMonth; /* Правка для правильного формата ввода в поле "Месяц". */
            } else {
                validMonth = Integer.toString(validMonthInt);
            }
        }
        return validMonth;
    }

    public static String generateInvalidMonth() { /* Возвращает прошедший месяц, работает только для текущего года. */
        String invalidMonth = null;
        if (currentMonthInt >= 2) { /* Нельзя генерировать отрицательные значения. */
            int currentMonthMinusOneInt = currentMonthInt - 1; /* Прошедший месяц. */
            int invalidMonthInt = faker.number().numberBetween(1, currentMonthMinusOneInt); /* Диапазон чисел от "1" до числа даты последнего прошедшего месяца. */
            if (invalidMonthInt <= 9) {
                invalidMonth = Integer.toString(invalidMonthInt);
                invalidMonth = "0" + invalidMonth; /* Правка для правильного формата ввода в поле "Месяц". */
            } else {
                invalidMonth = Integer.toString(invalidMonthInt);
            }
        }
        return invalidMonth; /* Невалидное значение. */
    }

    public static String generateHolder() { /* Возвращает случайное имя и фамилию. Часто ошибается со склонениями: не совпадают пол имени и фамилии. */
        String nameAndSurname = faker.name().firstName() + " " + faker.name().lastName();
        return nameAndSurname;
    }

    public static String generateInvalidHolder() { /* Возвращает случайный набор букв, цифр. Потом идут не случайные иероглифы и символы. */
        String invalidOwner = faker.bothify("#?#?#?#?#?#?#? ドミトリー ~!@#$%^&*()=+_[]{};:'\"<>?\\|/ლ(╹◡╹ლ)");
        return invalidOwner; /* Невалидное значение. */
    }

    public static String generateCVC() { /* Возвращает три случайные цифры. */
        String cvcCode = faker.numerify("###");
        return cvcCode;
    }

    public static String generateInvalidCvcFormat() { /* Возвращает две случайные цифры. */
        String invalidCvcFormat = String.valueOf(faker.number().numberBetween(0, 99));
        return invalidCvcFormat; /* Невалидное значение. */
    }

}