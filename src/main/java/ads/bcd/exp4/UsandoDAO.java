package ads.bcd.exp4;

import ads.bcd.exp4.entities.Pessoa;
import ads.bcd.exp4.entities.PessoaDAO;
import java.sql.SQLException;
import java.util.List;
/**
 * Esta classe demonstra como usar os métodos da classe PessoaDAO
 * para acessar os dados de forma simples e organizada.
 */
public class UsandoDAO {
    // String para formatar visualmente a saída
    private final String DIVISOR =
            "---------------------------------------------------------------------------------\n";
    /**
     * Método para cadastrar uma pessoa no banco, usando PessoaDAO.
     * @param p Objeto Pessoa com os dados a inserir.
     * @return true se o cadastro foi realizado com sucesso.
     * @throws SQLException em caso de erro ao acessar o banco.
     */
    public boolean cadastrarPessoa(Pessoa p) throws SQLException {
// Delegamos a responsabilidade de inserir ao PessoaDAO
        return PessoaDAO.adiciona(p);
    }
    /**
     * Método para listar todas as pessoas cadastradas, formatando a saída.
     * @return String formatada com os dados de todas as pessoas.
     * @throws SQLException em caso de erro na consulta.
     */
    public String listarPessoas() throws SQLException {
// Busca a lista de pessoas pelo DAO
        List<Pessoa> pessoas = PessoaDAO.listarTodas();
        StringBuilder sb = new StringBuilder();
// Cabeçalho da tabela
        sb.append(DIVISOR);
        sb.append(String.format("|%-5s|%-25s|%-10s|%-10s|%-25s|\n", "ID", "Nome", "Peso", "Altura", "Email"))
        ;
        sb.append(DIVISOR);
// Percorre a lista usando lambda para adicionar cada pessoa formatada na StringBuilder
        pessoas.forEach(pessoa -> sb.append(pessoa.toString()).append("\n"));
        sb.append(DIVISOR);
// Retorna a string com os dados organizados
        return sb.toString();
    }
}