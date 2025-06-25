package ads.bcd.exp5.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * Classe responsável por criar conexões com o banco MySQL.
 * As configurações ficam no arquivo database.properties dentro da pasta resources.
 */
public abstract class ConnectionFactory {
    // Nome do arquivo de configuração com as propriedades de conexão
    private static final String DB_PROPERTIES_FILE = "database.properties";
    private static Connection cnx; // Instância única de conexão (singleton simplificado)
    /**
     * Carrega o arquivo de propriedades do diretório resources.
     * Funciona dentro da IDE, testes de unidade e arquivos JAR.
     *
     * @return InputStream do arquivo properties
     * @throws IOException se o arquivo não for encontrado
     */
    private static InputStream getInputStream() throws IOException {
        InputStream is = ConnectionFactory.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_FILE);
        if (is == null) {
            throw new IOException("arquivo não encontrado " + DB_PROPERTIES_FILE);
        } else {
            return is;
        }
    }
    /**
     * Cria e retorna uma conexão ativa com o banco MySQL.
     * Usa as configurações do arquivo database.properties para host, porta, banco, usuário e senha.
     *
     * @return Connection ativa com o banco de dados
     * @throws IOException se falhar ao carregar as configurações
     * @throws SQLException se falhar a conexão com o banco
     */
    public static synchronized Connection getDBConnection() throws IOException, SQLException {
        Properties properties = new Properties();
        try {
// Carrega as propriedades do arquivo
            properties.load(getInputStream());
// Obtém os dados necessários para a URL de conexão
            String host = properties.getProperty("host");
            String port = properties.getProperty("port");
            String dbname = properties.getProperty("database");
// Monta a URL para conexão com MySQL
            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
// Cria a conexão usando a URL e as propriedades (que incluem usuário e senha)
            cnx = DriverManager.getConnection(url, properties);
        } catch (SQLException ex) {
            throw new SQLException("erro com instrução SQL", ex);
        } catch (IOException ex) {
            throw new IOException("arquivo properties não encontrado", ex);
        }
        return cnx; // Retorna a conexão criada
    }
}