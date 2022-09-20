import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionConfig {
    public static Connection getConnection() {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/bibliotek?serverTimezone=CET&useSSL=false";
        String urlBerry = "jdbc:mysql://localhost:3306/bibliotek?serverTimezone=CET&useSSL=false";
        String user = "root";
        String password = "oliver";
        String passwordBerry = "Sebsej123";

        try {
            connection = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
