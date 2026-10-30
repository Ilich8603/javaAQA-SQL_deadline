package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;


public class DataHelper {
    private static final Faker FAKER = new Faker();

    private DataHelper() {
    }

    public static AuthInfo getAuthInfoTestUserData() {

        return new AuthInfo("vasya", "qwerty123");
    }

    public static String generateRandomLogin() {
        return FAKER.name().username();
    }

    public static String generateRandomPassword() {
        return FAKER.internet().password();
    }

    public static AuthInfo generateRandomUser() {

        return new AuthInfo(generateRandomLogin(), generateRandomPassword());
    }

    public static VerificationCode generateRandomCode() {
        return new VerificationCode(FAKER.numerify("######"));
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerificationCode {
        String code;
    }
}
