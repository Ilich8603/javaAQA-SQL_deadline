package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.generateRandomPassword;
import static ru.netology.data.SQLHelper.cleanAuthCode;
import static ru.netology.data.SQLHelper.cleanDatabase;
import static com.codeborne.selenide.Selenide.open;

public class BankLoginTest {
    LoginPage loginPage;
    DataHelper.AuthInfo authInfo = DataHelper.getAuthInfoTestUserData();

    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @AfterEach
    void tearDown() {
        cleanAuthCode();
    }

    @BeforeEach
    void setup() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    @DisplayName("Should successfully login with exist user data from SUT test data")
    void shouldSuccessfulLogin() {
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should get error message if user is not exist in database")
    void shouldGetErrorNotificationWithRandomUserNotAddedToBase() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.login(authInfo);
        loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("Should get error message if user exist in database and active user and random code")
    void shouldGetErrorNotificationWithExistUserAndRandomVerificationCode() {
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.generateRandomCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }

    @Test
    @DisplayName("Should block user after three failed login attempts with wrong password")
    void shouldBlockUserAfterThreeFailedAttempts() {
        var wrongPassword = DataHelper.generateRandomPassword();
        loginPage.fillLogin(authInfo.getLogin());

        for (int i = 1; i <= 3; i++) {
            loginPage.fillPassword(wrongPassword);
            if (i < 3) {
                loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
            }
        }

        loginPage.verifyErrorNotification("Пользователь заблокирован");
        String actualStatus = SQLHelper.getUserStatus(authInfo.getLogin());
        assertEquals("blocked", actualStatus, "Статус пользователя 'blocked'");
    }


}
