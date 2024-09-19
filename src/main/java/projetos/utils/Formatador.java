package projetos.utils;


import java.text.NumberFormat;
import java.util.Locale;

public class Formatador {

    public static String formatarMoeda(double valor) {
        NumberFormat formatadorDeMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatadorDeMoeda.format(valor);
    }
}