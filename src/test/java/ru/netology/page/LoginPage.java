package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__content");



    public void login(DataHelper.AuthInfo info) {
        clearAndType(loginField, info.getLogin());
        clearAndType(passwordField, info.getPassword());
        loginButton.click();
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        login(info);
        return new VerificationPage();
    }

    public void fillLogin(String login) {
        clearAndType(loginField, login);
    }


    public void fillPassword(String password) {
        clearAndType(passwordField, password);
        loginButton.click();
    }

    public void verifyErrorNotification(String expectedText) {
        errorNotification.shouldHave(exactText(expectedText)).shouldBe(visible);
    }

    private void clearAndType(SelenideElement element, String text) {
        element.click();
        element.press(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        element.setValue(text);
    }

}