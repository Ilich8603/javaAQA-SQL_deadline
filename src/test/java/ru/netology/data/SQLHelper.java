package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import ru.netology.data.DataHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode() {
        var dataSQL = "select ac.code from auth_codes ac order by ac.created DESC limit 1;";
        try(var conn = getConnection()) {
            return runner.query(conn, dataSQL, new BeanHandler<>(DataHelper.VerificationCode.class));
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try(var conn = getConnection()) {
            runner.execute(conn, "delete from auth_codes ac");
            //runner.execute(conn, "delete from card_transactions ct");
            //runner.execute(conn, "delete from cards c");
            runner.update(conn, "UPDATE users SET status = 'active' WHERE status <> 'active';");
        }
    }

    @SneakyThrows
    public static void cleanAuthCode() {
        try(var conn = getConnection()) {
            runner.execute(conn, "delete from auth_codes ac");
        }
    }

    @SneakyThrows
    public static String getUserStatus(String login) {
        String sql = "SELECT status FROM users WHERE login = ?";
        try (Connection conn = getConnection()) {
            return runner.query(conn, sql, new ScalarHandler<>(), login);
        }
    }

    @SneakyThrows
    public static List<DataHelper.AuthInfo> getUsers() {
        var usersSQL = "SELECT * FROM users;";
        try (var conn = getConnection()) {
            return runner.query(conn, usersSQL, new BeanListHandler<>(DataHelper.AuthInfo.class));
        }
    }

}
