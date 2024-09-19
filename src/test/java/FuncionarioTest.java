import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projetos.Funcionario;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FuncionarioTest {

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario("João", 3000.00);
    }

    @Test
    void testCriacaoFuncionario() {
        assertEquals("João", funcionario.getNome());
        assertEquals(0, funcionario.getBonus());
        assertEquals(3000.00, funcionario.getSalarioBase(), 0.01);
    }

    @Test
    void testAdicionarBonus() {
        funcionario.adicionarBonus();
        assertEquals(10, funcionario.getBonus());

        funcionario.adicionarBonus();
        assertEquals(20, funcionario.getBonus());
    }

    @Test
    void testCalculoSalarioBaseComBonus() {
        assertEquals(3000.00, funcionario.getSalarioBase(), 0.01);

        funcionario.adicionarBonus();
        assertEquals(3000.00 + (10 / 30) * 100, funcionario.getSalarioBase(), 0.01);

        funcionario.adicionarBonus();
        assertEquals(3000.00 + (20 / 30) * 100, funcionario.getSalarioBase(), 0.01);
    }

    @Test
    void testToString() {
        String expectedString = "Funcionário: João, bônus: 0, salário base: R$ 3.000,00";
        assertEquals(expectedString, funcionario.toString());
    }
}
