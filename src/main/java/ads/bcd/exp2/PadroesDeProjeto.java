package ads.bcd.exp2;

import ads.bcd.exp2.db.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PadroesDeProjeto {
    private final String DIVISOR ="─────────────────────────────────────────────────────────────────────────────────\n";

    public String listarPessoas() throws SQLException {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM Pessoa";

        try (
                Connection conexao = ConnectionFactory.getDBConnection();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (!rs.next()) {
                sb.append("\nNenhuma pessoa cadastrada no banco\n");

            } else {
                sb.append(DIVISOR);
                sb.append(String.format("|%-5s|%-25s|%-10s|%-10s|%-25s|\n", "ID", "Nome", "Peso", "Altura", "Email"));sb.append(DIVISOR);
                do {
                    sb.append(String.format("|%-5d|%-25s|%-10.2f|%-10d|%-25s|\n",
                            rs.getInt("idPessoa"),
                            rs.getString("Nome"),
                            rs.getDouble("peso"),
                            rs.getInt("altura"),
                            rs.getString("email")));
                } while (rs.next());
                sb.append(DIVISOR);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return sb.toString();
    }
}
