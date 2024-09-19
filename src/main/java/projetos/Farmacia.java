package projetos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Farmacia {
    private double lucro;
    private final List<Medicamento> medicamentos;
    private final List<Funcionario> funcionarios;

    public Farmacia(List<Medicamento> medicamentos, List<Funcionario> funcionarios) {
        this.lucro = 0;
        this.medicamentos = medicamentos;
        this.funcionarios = funcionarios;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void comprarMedicamento() {
        String nomeMedicamento = obterEntradaUsuario("Digite o nome do medicamento:");

        Optional<Medicamento> medicamentoOpt = medicamentos.stream()
                .filter(m -> m.getNome().equalsIgnoreCase(nomeMedicamento))
                .findFirst();

        medicamentoOpt.ifPresentOrElse(this::processarCompra,
                () -> System.out.println("Medicamento não encontrado"));
    }

    private void processarCompra(Medicamento medicamento) {
        if (medicamento.getQuantidadeEmEstoque() == 0) {
            System.out.println("Medicamento fora de estoque");
            return;
        }

        int quantidade = obterQuantidadeMedicamentos(medicamento);
        String nomeFuncionario = obterEntradaUsuario("Digite o nome do funcionário (A, B, C, D):");

        Optional<Funcionario> funcionarioOpt = funcionarios.stream()
                .filter(f -> f.getNome().equalsIgnoreCase(nomeFuncionario))
                .findFirst();

        funcionarioOpt.ifPresentOrElse(funcionario -> finalizarCompra(medicamento, funcionario, quantidade),
                () -> System.out.println("Funcionário não encontrado"));
    }

    public int obterQuantidadeMedicamentos(Medicamento medicamento) {
        while (true) {
            try {
                int quantidade = Integer.parseInt(obterEntradaUsuario(
                        "Quantos " + medicamento.getNome() + " você quer comprar?"));
                if (quantidade > 0 && medicamento.getQuantidadeEmEstoque() >= quantidade) {
                    return quantidade;
                }
                System.out.println("Entrada inválida ou insuficiente em estoque. Digite um número válido.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número válido.");
            }
        }
    }

    private void finalizarCompra(Medicamento medicamento, Funcionario funcionario, int quantidade) {
        medicamento.setQuantidadeEmEstoque(medicamento.getQuantidadeEmEstoque() - quantidade);
        lucro += medicamento.getPreco() * quantidade;
        funcionario.adicionarBonus();
        exibirLucro();
    }

    public String obterEntradaUsuario(String mensagem) {
        System.out.println(mensagem);
        return new Scanner(System.in).nextLine();
    }

    public void limparHistorico() {
        lucro = 0;
        funcionarios.forEach(funcionario -> funcionario.setBonus(0));
        System.out.println("Histórico limpo com sucesso!");
    }

    public void listarMedicamentos() {
        System.out.println("Medicamentos:");
        medicamentos.forEach(System.out::println);
    }

    public void listarFuncionarios() {
        System.out.println("Funcionários:");
        funcionarios.forEach(System.out::println);
    }

    public void exibirLucro() {
        System.out.println("Lucro atual da farmácia: " + Formatador.formatarMoeda(lucro));
    }

    public void listarInformacoesFarmacia() {
        System.out.println(this);
    }

    public double getLucro() {
        return lucro;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append("Informações da Farmácia:\n");
        info.append("Lucro: ").append(Formatador.formatarMoeda(lucro)).append("\n");
        info.append("Medicamentos em estoque:\n");
        medicamentos.forEach(m -> info.append(m).append("\n"));
        info.append("Funcionários:\n");
        funcionarios.forEach(f -> info.append(f).append("\n"));
        return info.toString();
    }
}