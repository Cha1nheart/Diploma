package ru.netology.TourPurchase.data;

public class CardInfo {

//    Класс нужен для тестов ответов базы данных. Конструктор тела запроса.

    private String number;
    private String year;
    private String month;
    private String holder;
    private String cvc;

    public CardInfo(String number, String year, String month, String holder, String cvc) {
        this.number = number;
        this.year = year;
        this.month = month;
        this.holder = holder;
        this.cvc = cvc;
    }

//    Геттеры и сеттеры не используются, сгенерированы Lombok.

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
}