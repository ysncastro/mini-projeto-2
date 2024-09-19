import org.junit.jupiter.api.Test;
import projetos.Formatador;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MedicamentoTest {


    @Test
    void testCriacaoMedicamento() {
        assertEquals("Paracetamol", TestProvider.MEDICAMENTO_PARACETAMOL.getNome());
        assertEquals(100, TestProvider.MEDICAMENTO_PARACETAMOL.getQuantidadeEmEstoque());
        assertEquals(5.5, TestProvider.MEDICAMENTO_PARACETAMOL.getPreco(), 0.01);
    }

    @Test
    void testSetQuantidadeEmEstoque() {
        TestProvider.MEDICAMENTO_PARACETAMOL.setQuantidadeEmEstoque(100);
        assertEquals(100, TestProvider.MEDICAMENTO_PARACETAMOL.getQuantidadeEmEstoque());
    }

    @Test
    void testToString() {
        String expectedString = String.format("Medicamento: %s, quantidade: %d, preço: %s",
                TestProvider.MEDICAMENTO_PARACETAMOL.getNome(), TestProvider.MEDICAMENTO_PARACETAMOL.getQuantidadeEmEstoque(), Formatador.formatarMoeda(TestProvider.MEDICAMENTO_PARACETAMOL.getPreco()));
        assertEquals(expectedString, TestProvider.MEDICAMENTO_PARACETAMOL.toString());
    }
}
