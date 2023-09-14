package ru.netology.TourPurchase.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("ru_RU"));
    public static String firstCardNumber = "4444444444444441";
    public static String secondCardNumber = "4444444444444442";
    public static String currentYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    public static String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    public static int currentMonthInt = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
    public static int currentYearInt = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yy")));

    public static String generateInvalidCardNumberFormat() { /* Возвращает 15 случайных цифр. */
        String invalidCardNumberFormat = faker.numerify("###############");
        return invalidCardNumberFormat; /* Невалидное значение. */
    }

    public static String generateInvalidCardNumber() { /* Возвращает 16 случайных цифр, кроме значений первой и второй карты. */
        String invalidCardNumber = faker.numerify("################");
        for (int i = 0; invalidCardNumber == firstCardNumber || invalidCardNumber == secondCardNumber; i++) {
            invalidCardNumber = faker.numerify("################");
        }
        return invalidCardNumber; /* Невалидное значение. */
    }

    public static String generateYear() { /* Возвращает валидные значения для года согласно сроку действия дебетовых карт(до 5 лет). */
        String validYear;
        int currentYearPlusExpire = currentYearInt + 5;
        int validYearInt = faker.number().numberBetween(currentYearInt, currentYearPlusExpire);
        if (validYearInt <= 9) {
            validYear = Integer.toString(validYearInt);
            validYear = "0" + validYear;
        } else {
            validYear = Integer.toString(validYearInt);
        }
        return validYear;
    }

    public static String generateInvalidMonthOrYearFormat() { /* Возвращает одну из двух цифр(0, 9). */
        String invalidYearFormat = String.valueOf(faker.number().numberBetween(0, 9));
        return invalidYearFormat; /* Невалидное значение. */
    }

    public static String generateInvalidYearPassed() { /* Возвращает случайный прошедший год согласно текущей дате. */
        String invalidYearPassed;
        if (currentYearInt >= 1) {
            int invalidYearPassedMinusOneInt = currentYearInt - 1;
            int invalidYearPassedInt = faker.number().numberBetween(0, invalidYearPassedMinusOneInt);
            if (invalidYearPassedInt <= 9) {
                invalidYearPassed = Integer.toString(invalidYearPassedInt);
                invalidYearPassed = "0" + invalidYearPassed;
            } else {
                invalidYearPassed = Integer.toString(invalidYearPassedInt);
            }
        } else {
            int invalidYearPassedInt = 99;
            invalidYearPassed = Integer.toString(invalidYearPassedInt);
        }
        return invalidYearPassed; /* Невалидное значение. */
    }

    public static String generateInvalidYearExpired() { /* Возвращает год с нереальным сроком действия дебетовой карты согласно текущей дате. */
        String invalidYearExpired;
        if (currentYearInt <= 93) {
            int currentYearPlusExpire = currentYearInt + 6;
            int invalidYearExpiredInt = faker.number().numberBetween(currentYearPlusExpire, 99);
            if (invalidYearExpiredInt <= 9) {
                invalidYearExpired = Integer.toString(invalidYearExpiredInt);
                invalidYearExpired = "0" + invalidYearExpired;
            } else {
                invalidYearExpired = Integer.toString(invalidYearExpiredInt);
            }
        } else {
            invalidYearExpired = "00";
        }
        return invalidYearExpired; /* Невалидное значение. */
    }

    public static String validYear = generateYear(); /* Переменная понадобится для метода ниже. */

    public static String generateMonth() { /* Возвращает случайный месяц согласно текущей дате. */
        String validMonth;
        if (Integer.parseInt(validYear) > currentYearInt) {
            int validMonthInt = faker.number().numberBetween(1, 12);
            if (validMonthInt <= 9) {
                validMonth = Integer.toString(validMonthInt);
                validMonth = "0" + validMonth;
            } else {
                validMonth = Integer.toString(validMonthInt);
            }
        } else {
            int validMonthInt = faker.number().numberBetween(currentMonthInt, 12);
            if (validMonthInt <= 9) {
                validMonth = Integer.toString(validMonthInt);
                validMonth = "0" + validMonth;
            } else {
                validMonth = Integer.toString(validMonthInt);
            }
        }
        return validMonth;
    }

    public static String generateInvalidMonth() { /* Возвращает прошедший месяц, работает только для текущего года. */
        String invalidMonth = null;
        if (currentMonthInt >= 2) {
            int currentMonthMinusOneInt = currentMonthInt - 1;
            int invalidMonthInt = faker.number().numberBetween(1, currentMonthMinusOneInt);
            if (invalidMonthInt <= 9) {
                invalidMonth = Integer.toString(invalidMonthInt);
                invalidMonth = "0" + invalidMonth;
            } else {
                invalidMonth = Integer.toString(invalidMonthInt);
            }
        }
        return invalidMonth; /* Невалидное значение. */
    }

    public static String generateHolder() { /* Возвращает случайное имя и фамилию, бывают недочеты: женское имя и мужская фамилия. */
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