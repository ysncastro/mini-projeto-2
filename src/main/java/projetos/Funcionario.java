package projetos;

public class Funcionario {
    private String nome;
    private int bonus;
    private double salarioBase;

    public Funcionario(String nome, double salarioBase) {
        this.nome = nome;
        this.bonus = 0;
        this.salarioBase = salarioBase; // Houveram algumas dúvidas em relação à esse ponto no grupo. Foi encaminhado um vídeo em que parece ser dito que começaria em zero também, mas como não foi especificado no pdf, segui essa abordagem!)
    }

    public String getNome() {
        return nome;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public double getSalarioBase() {
        return salarioBase + (bonus / 30) * 100;
    }

    public void adicionarBonus() {
        this.bonus += 10;
    }

    @Override
    public String toString() {
        return String.format("Funcionário: %s, bônus: %d, salário base: %s",
                nome, bonus, Formatador.formatarMoeda(getSalarioBase()));
    }
}