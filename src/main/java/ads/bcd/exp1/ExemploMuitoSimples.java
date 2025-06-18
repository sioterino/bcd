package ads.bcd.exp1;

import java.sql.*;
import org.sqlite.SQLiteConfig;

public class ExemploMuitoSimples {

    // Outro que permite passar um URI diferente para o banco.
    private String DB_URI = "jdbc:sqlite:lab01.sqlite";
    // Configurações específicas do SQLite, como ativar restrição de chaves estrangeiras.
    private SQLiteConfig sqLiteConfig;
    private final String DIVISOR ="─────────────────────────────────────────────────────────────────────────────────\n";

    // Outro que permite passar um URI diferente para o banco.
    public ExemploMuitoSimples(String dB_URI) {
        this();
        DB_URI = dB_URI;
    }

    // Um construtor padrão que inicializa a configuração do SQLite.
    public ExemploMuitoSimples() {
        this.sqLiteConfig = new SQLiteConfig();
        sqLiteConfig.enforceForeignKeys(true); // ativa restrição de chaves estrangeiras
    }

    // Insere uma nova pessoa na tabela Pessoa.
    public int cadastrarPessoa(String nome, double peso, int altura, String email) throws SQLException {
        int resultado = -1;

        try (
                Connection conexao = DriverManager.getConnection(DB_URI, this.sqLiteConfig.toProperties());
                Statement stmt = conexao.createStatement()
        ) {
            String sql = "INSERT INTO Pessoa (nome, peso, altura, email) VALUES ('" + nome + "'," + peso + "," + altura + ",'" + email + "')";
            resultado = stmt.executeUpdate(sql);

        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar pessoa", e);
        }

        return resultado;
    }

    // Atualiza os dados da pessoa com o idPessoa informado.
    public int alterarDadosPessoa(int idPessoa, String nome, double peso, int altura, String email) throws SQLException {
        int resultado = -1;

        try (
                Connection conexao = DriverManager.getConnection(DB_URI, this.sqLiteConfig.toProperties());
                Statement stmt = conexao.createStatement()
        ) {
            String sql = "UPDATE Pessoa SET nome = '" + nome + "', peso=" + peso + ", altura=" + altura + ", email = '" + email + "' WHERE idPessoa=" + idPessoa;
            resultado = stmt.executeUpdate(sql);

        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar dados de pessoas", e);
        }

        return resultado;
    }

    // Exclui uma pessoa do banco pelo ID.
    public int excluirPessoa(int idPessoa) throws SQLException {
        int resultado = -1;

        try (
                Connection conexao = DriverManager.getConnection(DB_URI, this.sqLiteConfig.toProperties());
                Statement stmt = conexao.createStatement()
        ) {
            String sql = "DELETE FROM Pessoa WHERE idPessoa = " + idPessoa;
            resultado = stmt.executeUpdate(sql);

        } catch (SQLException e) {
            throw new SQLException("Erro ao excluir pessoas", e);
        }

        return resultado;
    }

    public String listarRegistros() throws SQLException {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM Pessoa";

        try (
                Connection conexao = DriverManager.getConnection(DB_URI, this.sqLiteConfig.toProperties());
                Statement stmt = conexao.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (!rs.next()) {
                sb.append("\nNenhuma pessoa cadastrada no banco\n");
            } else {
                sb.append(DIVISOR);
                sb.append(String.format(
                        "|%-5s|%-25s|%-10s|%-10s|%-25s|\n",
                        "ID", "Nome", "Peso", "Altura", "Email"));
                sb.append(DIVISOR);

                do {
                    sb.append(String.format(
                            "|%-5d|%-25s|%-10.2f|%-10d|%-25s|\n",
                            rs.getInt("idPessoa"),
                            rs.getString("Nome"),
                            rs.getDouble("peso"),
                            rs.getInt("altura"),
                            rs.getString("email")));
                } while (rs.next());

                sb.append(DIVISOR);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar todas pessoas", e);
        }

        return sb.toString();
    }

    public String listarDadosPessoa(String emailPessoa) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM Pessoa WHERE Email = ?";

        try (
                Connection conexao = DriverManager.getConnection(DB_URI, this.sqLiteConfig.toProperties());
                PreparedStatement pstmt = conexao.prepareStatement(sql)
        ) {
            pstmt.setString(1, emailPessoa);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    sb.append("\nNenhuma pessoa cadastrada possui o email informado\n");
                } else {
                    do {
                        sb.append(String.format(
                                "%d|%s|%.2f|%d|%s\n",
                                rs.getInt("idPessoa"),
                                rs.getString("Nome"),
                                rs.getDouble("peso"),
                                rs.getInt("altura"),
                                rs.getString("email")
                        ));
                    } while (rs.next());
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar dados de pessoas", e);
        }

        return sb.toString();
    }

    public boolean criaBancoDeDados() throws Exception {
        try (
                Connection conexao = DriverManager.getConnection(DB_URI, this.sqLiteConfig.toProperties());
                Statement statement = conexao.createStatement()
        ) {
            statement.executeUpdate("DROP TABLE IF EXISTS Pessoa");

            statement.executeUpdate(
                    "CREATE TABLE Pessoa (" +
                            "idPessoa INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                            "Nome TEXT NOT NULL, " +
                            "Peso REAL, " +
                            "Altura INTEGER, " +
                            "Email TEXT)"
            );

            statement.executeUpdate(
                    "INSERT INTO Pessoa (Nome, Peso, Altura, Email) " +
                            "VALUES ('Aluno Teste', 85.2, 180, 'aluno@teste.com.br')"
            );
        } catch (Exception e) {
            throw new Exception("Erro ao criar tabelas", e);
        }

        return true;
    }


}
