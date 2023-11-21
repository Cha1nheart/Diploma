package ru.netology.TourPurchase.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLhelper {

//    Переменные: URL для MySQl, URL для PostgreSQL, имя пользователя и пароль.

    private static final String MySQLdbURL = System.getProperty("datasource.MySQLdbURL");
    private static final String PostgreSQLdbURL = System.getProperty("datasource.PostgreSQLdbURL");
    private static final String username = System.getProperty("username");
    private static final String password = System.getProperty("password");

    private static QueryRunner runner = new QueryRunner();

    private SQLhelper() {
    }

//    Методы создания соединений.

    private static Connection getConnToMySQLdb() throws SQLException { /* MySQL соединение. */
        return DriverManager.getConnection(MySQLdbURL, username, password);
    }

    private static Connection getConnToPostgreSQLdb() throws SQLException { /* PostgreSQL соединение. */
        return DriverManager.getConnection(PostgreSQLdbURL, username, password);
    }

//    MySQL методы при обычной покупке тура.

    @SneakyThrows
    public static String getStatusFromPaymentMySQL() { /* Статус */
      Thread.sleep(10000);
      var paymentStatusSQL = "SELECT status FROM payment_entity order by created desc LIMIT 1";
      var connection = getConnToMySQLdb();
      var result = runner.query(connection, paymentStatusSQL, new ScalarHandler<String>());
      return result;
    }

    @SneakyThrows
    public static int getAmountFromPaymentMySQL() { /* Сумма */
        Thread.sleep(10000);
        var paymentAmountSQL = "SELECT amount FROM payment_entity order by created desc LIMIT 1";
        var connection = getConnToMySQLdb();
        var result = runner.query(connection, paymentAmountSQL, new ScalarHandler<Integer>());
        return result;
    }

//    MySQL методы при покупке тура в кредит.

    @SneakyThrows
    public static String getStatusFromCreditRequestMySQL() { /* Статус */
        Thread.sleep(10000);
        var creditRequestStatusSQL = "SELECT status FROM credit_request_entity order by created desc LIMIT 1";
        var connection = getConnToMySQLdb();
        var result = runner.query(connection, creditRequestStatusSQL, new ScalarHandler<String>());
        return result;
    }

    @SneakyThrows
    public static int getAmountFromCreditRequestMySQL() { /* Сумма */
        Thread.sleep(10000);
        var creditRequestAmountSQL = "SELECT amount FROM credit_request_entity order by created desc LIMIT 1";
        var connection = getConnToMySQLdb();
        var result = runner.query(connection, creditRequestAmountSQL, new ScalarHandler<Integer>());
        return result;
    }



//    PostgreSQL методы при обычной покупке тура.
    @SneakyThrows
    public static String getStatusFromPaymentPostgreSQL() { /* Статус */
        Thread.sleep(10000);
        var paymentStatusPostgreSQL = "SELECT status FROM payment_entity order by created desc LIMIT 1";
        var connection = getConnToPostgreSQLdb();
        var result = runner.query(connection, paymentStatusPostgreSQL, new ScalarHandler<String>());
        return result;
    }

    @SneakyThrows
    public static int getAmountFromPaymentPostgreSQL() { /* Сумма */
        Thread.sleep(10000);
        var paymentAmountPostgreSQL = "SELECT amount FROM payment_entity order by created desc LIMIT 1";
        var connection = getConnToPostgreSQLdb();
        var result = runner.query(connection, paymentAmountPostgreSQL, new ScalarHandler<Integer>());
        return result;
    }

//    PostgreSQL методы при покупке тура в кредит.

    @SneakyThrows
    public static String getStatusFromCreditRequestPostgreSQL() { /* Статус */
        Thread.sleep(10000);
        var creditRequestStatusPostgreSQL = "SELECT status FROM credit_request_entity order by created desc LIMIT 1";
        var connection = getConnToPostgreSQLdb();
        var result = runner.query(connection, creditRequestStatusPostgreSQL, new ScalarHandler<String>());
        return result;
    }

    @SneakyThrows
    public static int getAmountFromCreditRequestPostgreSQL() { /* Сумма */
        Thread.sleep(10000);
        var creditRequestAmountPostgreSQL = "SELECT amount FROM credit_request_entity order by created desc LIMIT 1";
        var connection = getConnToPostgreSQLdb();
        var result = runner.query(connection, creditRequestAmountPostgreSQL, new ScalarHandler<Integer>());
        return result;
    }

//    Очистка базы данных. Выбор метода зависит от настроек "application.properties"(подробнее в тестовых классах и README.md).

    @SneakyThrows
    public static void MySQLcleanDataBase() {
        Thread.sleep(10000);
        var connection = getConnToMySQLdb();
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
    }

    @SneakyThrows
    public static void PostgreSQLcleanDataBase() {
        Thread.sleep(10000);
        var connection = getConnToPostgreSQLdb();
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
    }

}
