package ads.bcd.exp4.entities;

import ads.bcd.exp2.db.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Essa classe é responsável por acessar a tabela Pessoa no banco de dados.
 * Centraliza os comandos SQL e evita que eles fiquem espalhados no projeto.
 */
public abstract class PessoaDAO {
    /**
     * Método para adicionar uma nova pessoa no banco.
     * @param p Objeto Pessoa com os dados a inserir.
     * @return true se a operação foi bem sucedida.
     * @throws SQLException em caso de erro na conexão ou comando SQL.
     */
    public final static boolean adiciona(Pessoa p) throws SQLException {
        boolean resultado = false;
// Comando SQL para inserir os dados na tabela Pessoa
        String sql = "INSERT INTO Pessoa (nome, peso, altura, email) VALUES (?,?,?,?)";
// Try-with-resources para garantir que a conexão e statement sejam fechados automaticamente
        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
// Seta os valores para os parâmetros ? na ordem correta
            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getPeso());
            stmt.setInt(3, p.getAltura());
            stmt.setString(4, p.getEmail());
// Executa o comando SQL
            resultado = stmt.execute();
        } catch (SQLException ex) {
// Relança a exceção para ser tratada onde o método for chamado
            throw new SQLException(ex);
        }
        return resultado;
    }
    /**
     * Método para listar todas as pessoas cadastradas no banco.
     * @return Uma lista de objetos Pessoa.
     * @throws SQLException em caso de erro na consulta.
     */
    public final static List<Pessoa> listarTodas() throws SQLException {
        List<Pessoa> pessoas = new ArrayList<>();
// Comando SQL para selecionar todos os registros da tabela Pessoa
        String sql = "SELECT * FROM Pessoa";
        try (Connection conexao = ConnectionFactory.getDBConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
// Enquanto houver registro no resultado, cria objeto Pessoa e adiciona à lista
            while (rs.next()) {
                Pessoa c = new Pessoa(
                        rs.getString("nome"),
                        rs.getDouble("peso"),
                        rs.getInt("altura"),
                        rs.getString("email"));
                c.setIdPessoa(rs.getInt("idPessoa"));
                pessoas.add(c);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return pessoas;
    }
}