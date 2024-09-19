import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projetos.Farmacia;
import projetos.Funcionario;
import projetos.Medicamento;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class FarmaciaTest {

    private Farmacia farmacia;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        Medicamento medicamento1 = new Medicamento("Paracetamol", 100, 5.50);
        Medicamento medicamento2 = new Medicamento("Ibuprofeno", 0, 8.75);
        Funcionario funcionario = new Funcionario("A", 2500.00);
        List<Medicamento> medicamentos = Arrays.asList(medicamento1, medicamento2);
        List<Funcionario> funcionarios = List.of(funcionario);
        farmacia = new Farmacia(medicamentos, funcionarios);
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private Farmacia criarSpyFarmacia() {
        return spy(farmacia);
    }

    @Test
    void testComprarMedicamento_Sucesso() {
        Farmacia spyFarmacia = criarSpyFarmacia();
        doReturn("Paracetamol").when(spyFarmacia).obterEntradaUsuario("Digite o nome do medicamento:");
        doReturn("A").when(spyFarmacia).obterEntradaUsuario("Digite o nome do funcionário (A, B, C, D):");
        doReturn("1").when(spyFarmacia).obterEntradaUsuario("Quantos Paracetamol você quer comprar?");

        spyFarmacia.comprarMedicamento();

        Medicamento paracetamol = spyFarmacia.getMedicamentos().get(0);
        Funcionario funcionario = spyFarmacia.getFuncionarios().get(0);

        assertEquals(99, paracetamol.getQuantidadeEmEstoque());
        assertEquals(10, funcionario.getBonus());
        assertEquals(5.5, spyFarmacia.getLucro());

        String output = outputStream.toString().trim();
        assertEquals("Lucro atual da farmácia: R$ 5,50", output);
    }

    @Test
    void testComprarMedicamento_MedicamentoForaDeEstoque() {
        Farmacia spyFarmacia = criarSpyFarmacia();
        doReturn("Ibuprofeno").when(spyFarmacia).obterEntradaUsuario("Digite o nome do medicamento:");
        doReturn("A").when(spyFarmacia).obterEntradaUsuario("Digite o nome do funcionário (A, B, C, D):");
        doReturn("1").when(spyFarmacia).obterEntradaUsuario("Quantos Ibuprofeno você quer comprar?");

        spyFarmacia.comprarMedicamento();

        String output = outputStream.toString().trim();
        assertEquals("Medicamento fora de estoque", output);

        Medicamento ibuprofeno = spyFarmacia.getMedicamentos().get(1);
        assertEquals(0, ibuprofeno.getQuantidadeEmEstoque());
        assertEquals(0, spyFarmacia.getFuncionarios().get(0).getBonus());
        assertEquals(0, spyFarmacia.getLucro());
    }

    @Test
    void testComprarMedicamento_MedicamentoNaoEncontrado() {
        Farmacia spyFarmacia = criarSpyFarmacia();
        doReturn("Medicamento não existente").when(spyFarmacia).obterEntradaUsuario("Digite o nome do medicamento:");

        spyFarmacia.comprarMedicamento();

        verify(spyFarmacia).obterEntradaUsuario("Digite o nome do medicamento:");

        String output = outputStream.toString().trim();
        assertEquals("Medicamento não encontrado", output);

        assertEquals(0, spyFarmacia.getLucro());
    }

    @Test
    void testComprarMedicamento_FuncionarioNaoEncontrado() {
        Farmacia spyFarmacia = criarSpyFarmacia();
        doReturn("Paracetamol").when(spyFarmacia).obterEntradaUsuario("Digite o nome do medicamento:");
        doReturn("H").when(spyFarmacia).obterEntradaUsuario("Digite o nome do funcionário (A, B, C, D):");
        doReturn("2").when(spyFarmacia).obterEntradaUsuario("Quantos Paracetamol você quer comprar?");

        spyFarmacia.comprarMedicamento();

        verify(spyFarmacia).obterEntradaUsuario("Digite o nome do medicamento:");
        verify(spyFarmacia).obterEntradaUsuario("Digite o nome do funcionário (A, B, C, D):");

        String output = outputStream.toString().trim();
        assertEquals("Funcionário não encontrado", output);

        Medicamento paracetamol = spyFarmacia.getMedicamentos().get(0);
        assertEquals(100, paracetamol.getQuantidadeEmEstoque());
        assertEquals(0, spyFarmacia.getLucro());
    }

    @Test
    void testObterQuantidadeMedicamentos_EntradaValida() {
        Farmacia spyFarmacia = criarSpyFarmacia();
        doReturn("5").when(spyFarmacia).obterEntradaUsuario("Quantos Paracetamol você quer comprar?");

        Medicamento medicamento = new Medicamento("Paracetamol", 10, 5.50);
        int quantidade = spyFarmacia.obterQuantidadeMedicamentos(medicamento);

        assertEquals(5, quantidade);
    }

    @Test
    void testObterQuantidadeMedicamentos_EntradaInvalida() {
        Farmacia spyFarmacia = criarSpyFarmacia();
        doReturn("abc").doReturn("0").doReturn("5").when(spyFarmacia).obterEntradaUsuario("Quantos Paracetamol você quer comprar?");

        Medicamento medicamento = new Medicamento("Paracetamol", 10, 5.50);
        int quantidade = spyFarmacia.obterQuantidadeMedicamentos(medicamento);

        assertEquals(5, quantidade);
    }

    @Test
    void testLimparHistorico() {
        Funcionario funcionario = new Funcionario("A", 2500.00);
        funcionario.adicionarBonus();
        farmacia.getFuncionarios().get(0).adicionarBonus();

        farmacia.exibirLucro();

        farmacia.limparHistorico();

        assertEquals(0, farmacia.getLucro());
        assertEquals(0, farmacia.getFuncionarios().get(0).getBonus());

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Histórico limpo com sucesso!"));
    }

    @Test
    void testListarMedicamentos() {
        Farmacia spyFarmacia = criarSpyFarmacia();
        spyFarmacia.listarMedicamentos();

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Medicamentos:"));
        assertTrue(output.contains("Paracetamol"));
        assertTrue(output.contains("Ibuprofeno"));
    }

    //aaa

    @Test
    void testListarFuncionarios() {
        Farmacia spyFarmacia = criarSpyFarmacia();
        spyFarmacia.listarFuncionarios();

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Funcionários:"));
        assertTrue(output.contains("A"));
    }

    @Test
    void testExibirLucro() {
        Farmacia spyFarmacia = criarSpyFarmacia();
        spyFarmacia.exibirLucro();

        String output = outputStream.toString().trim();
        assertEquals("Lucro atual da farmácia: R$ 0,00", output);
    }

    @Test
    void testListarInformacoesFarmacia() {
        Farmacia spyFarmacia = criarSpyFarmacia();
        spyFarmacia.listarInformacoesFarmacia();

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Informações da Farmácia:"));
        assertTrue(output.contains("Lucro:"));
    }
}
