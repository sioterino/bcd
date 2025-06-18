package ads.bcd.exp2.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.sqlite.SQLiteConfig;

/**
 * Classe responsável por criar conexões com o banco
 */
public abstract class ConnectionFactory {

    private static final String DB_URI = "jdbc:sqlite:lab01.sqlite";
    private static Connection cnx;
    private static SQLiteConfig sqLiteConfig = new SQLiteConfig();

    public static synchronized Connection getDBConnection() throws SQLException {
        sqLiteConfig.enforceForeignKeys(true);
        try {
            cnx = DriverManager.getConnection(DB_URI, sqLiteConfig.toProperties());
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar no banco de dados", e);
        }
        return cnx;
    }
}