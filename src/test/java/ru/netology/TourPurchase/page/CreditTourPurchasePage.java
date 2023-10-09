package ru.netology.TourPurchase.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class CreditTourPurchasePage {

    public static SelenideElement creditTourPurchaseButton = $$("[role=\'button\']")
            .findBy(Condition.exactText("Купить в кредит"));
}
