package ru.netology.TourPurchase.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class TourPurchasePage {

    public static SelenideElement tourPurchaseButton = $$("[role=\'button\']")
                .findBy(Condition.exactText("Купить"));
}
