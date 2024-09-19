package farmacia;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import projetos.utils.Formatador;
import utils.TestProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MedicamentoTest {


    @Test
    void testCriacaoMedicamento() {
        Assertions.assertEquals("Paracetamol", TestProvider.MEDICAMENTO_PARACETAMOL.getNome());
        Assertions.assertEquals(99, TestProvider.MEDICAMENTO_PARACETAMOL.getQuantidadeEmEstoque());
        Assertions.assertEquals(5.5, TestProvider.MEDICAMENTO_PARACETAMOL.getPreco(), 0.01);
    }

    @Test
    void testSetQuantidadeEmEstoque() {
        TestProvider.MEDICAMENTO_PARACETAMOL.setQuantidadeEmEstoque(100);
        Assertions.assertEquals(100, TestProvider.MEDICAMENTO_PARACETAMOL.getQuantidadeEmEstoque());
    }

    @Test
    void testToString() {
        String expectedString = String.format("Medicamento: %s, quantidade: %d, preço: %s",
                TestProvider.MEDICAMENTO_PARACETAMOL.getNome(), TestProvider.MEDICAMENTO_PARACETAMOL.getQuantidadeEmEstoque(), Formatador.formatarMoeda(TestProvider.MEDICAMENTO_PARACETAMOL.getPreco()));
        Assertions.assertEquals(expectedString, TestProvider.MEDICAMENTO_PARACETAMOL.toString());
    }
}
