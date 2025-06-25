package ads.bcd.exp5;

import ads.bcd.exp5.db.ConnectionFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Classe que demonstra como conectar ao banco MySQL e listar dados da tabela Departamento.
 * O script para criação do banco está em resources/lab01-mysql-dml-ddl.sql.
 */
public class ExemploMySQL {
    /**
     * Método que lista todos os departamentos, formatando a saída.
     * @return String formatada com dados da tabela Departamento.
     * @throws IOException caso haja erro na conexão.
     */
    public String listarDadosDeTodosDepartamentos() throws IOException {
        StringBuilder sb = new StringBuilder(); // Para construir a string com a saída formatada
// Consulta SQL para buscar todos os dados da tabela Departamento
        String query = "SELECT * FROM Departamento";
// Try-with-resources para garantir fechamento automático de recursos
        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
// Verifica se existe algum registro retornado
            if (rs.next()) {
                sb.append("------------------------------------------------------\n");
                sb.append(String.format("|%-5s|%-35s|%10s|\n", "ID", "Nome", "Orçamento"));
                sb.append("------------------------------------------------------\n");
// Percorre todos os registros do ResultSet
                do {
                    int idDepto = rs.getInt("idDepartamento"); // Obtém idDepartamento
                    String dNome = rs.getString("dNome"); // Obtém nome do departamento
                    double orcamento = rs.getDouble("Orcamento"); // Obtém orçamento
// Adiciona linha formatada à saída
                    sb.append(String.format("|%-5d|%-35s|%10.2f|\n", idDepto, dNome, orcamento));
                } while (rs.next());
                sb.append("------------------------------------------------------\n");
            } else {
                sb.append("Não há registros no banco de dados\n");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Exibe o erro caso ocorra na consulta
        }
        return sb.toString(); // Retorna a string com a tabela formatada
    }
}
