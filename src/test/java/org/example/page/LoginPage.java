package org.example.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.example.data.DataHelper;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");
    private final SelenideElement blockInfo = $(Selectors.withText("Форма заблокирована!"));

    public void verifyErrorNotificationVisibility() {
        errorNotification.shouldBe(visible);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
    public void clear() {
        loginField.sendKeys(Keys.CONTROL + "A");
        loginField.sendKeys(Keys.BACK_SPACE);
        passwordField.sendKeys(Keys.CONTROL + "A");
        passwordField.sendKeys(Keys.BACK_SPACE);
    }

    public void getBlockInfo() {
        blockInfo.shouldBe(visible);
    }
}