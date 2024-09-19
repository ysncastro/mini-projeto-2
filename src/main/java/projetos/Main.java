package projetos;

import projetos.farmacia.Farmacia;
import projetos.farmacia.Funcionario;
import projetos.farmacia.Medicamento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Medicamento> medicamentos = new ArrayList<>(Arrays.asList(
                new Medicamento("Paracetamol", 100, 5.50),
                new Medicamento("Ibuprofeno", 50, 8.75)
        ));

        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
                new Funcionario("A", 2500.00),
                new Funcionario("B", 1800.00),
                new Funcionario("C", 2200.00),
                new Funcionario("D", 1900.00)
        ));

        Farmacia farmacia = new Farmacia(medicamentos, funcionarios);

        int opcao;
        do {
            exibirMenu();
            opcao = obterOpcao();
            processarOpcao(farmacia, opcao);
        } while (opcao != 6);

    }

    private static void exibirMenu() {
        System.out.println("\nBem-vindo(a)!");
        System.out.println("Qual ação você deseja realizar?");
        System.out.println("[1] Listar informações da farmácia");
        System.out.println("[2] Listar funcionários");
        System.out.println("[3] Listar medicamentos");
        System.out.println("[4] Comprar medicamentos");
        System.out.println("[5] Limpar histórico");
        System.out.println("[6] Sair");
    }

    private static int obterOpcao() {
        while (true) {
            try {
                System.out.print("Escolha uma opção: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
            }
        }
    }

    private static void processarOpcao(Farmacia farmacia, int opcao) {
        switch (opcao) {
            case 1 -> farmacia.listarInformacoesFarmacia();
            case 2 -> farmacia.listarFuncionarios();
            case 3 -> farmacia.listarMedicamentos();
            case 4 -> farmacia.comprarMedicamento();
            case 5 -> farmacia.limparHistorico();
            case 6 -> System.out.println("Saindo...");
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }
}
