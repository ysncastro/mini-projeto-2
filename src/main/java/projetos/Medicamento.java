package projetos;

public class Medicamento {
    private String nome;
    private int quantidadeEmEstoque;
    private double preco;

    public Medicamento(String nome, int quantidadeEmEstoque, double preco) {
        this.nome = nome;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return String.format("Medicamento: %s, quantidade: %d, preço: %s",
                nome, quantidadeEmEstoque, Formatador.formatarMoeda(preco));
    }
}