package ads.bcd.exp4.entities;

/**
 * Classe que representa uma pessoa.
 * Cada atributo corresponde a uma coluna na tabela Pessoa.
 */
public class Pessoa {
    // Atributo que representa o identificador único da pessoa no banco
    private int idPessoa;
    // Nome da pessoa
    private String nome;
    // Peso da pessoa em kg
    private double peso;
    // Altura da pessoa em centímetros
    private int altura;
    // Email da pessoa
    private String email;
    // Construtor vazio necessário para frameworks e uso geral
    public Pessoa() { }
    // Construtor para inicializar a pessoa com dados
    public Pessoa(String nome, double peso, int altura, String email) {
        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.email = email;
    }
    // Getter para o idPessoa
    public int getIdPessoa() {
        return idPessoa;
    }
    // Setter para o idPessoa
    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }
    // Getter para o nome
    public String getNome() {
        return nome;
    }
    // Setter para o nome
    public void setNome(String nome) {
        this.nome = nome;
    }
    // Getter para o peso
    public double getPeso() {
        return peso;
    }
    // Setter para o peso
    public void setPeso(double peso) {
        this.peso = peso;
    }
    // Getter para a altura
    public int getAltura() {
        return altura;
    }
    // Setter para a altura
    public void setAltura(int altura) {
        this.altura = altura;
    }
    // Getter para o email
    public String getEmail() {
        return email;
    }
    // Setter para o email
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Método que retorna uma string formatada da pessoa,
     * muito útil para exibir os dados organizadamente no console.
     */
    @Override
    public String toString() {
        return String.format("|%-5d|%-25s|%-10.2f|%-10d|%-25s|",
                idPessoa, nome, peso, altura, email);
    }
}
