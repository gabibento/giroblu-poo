package model;

public class PlanoMensal implements Plano {

    private static final long serialVersionUID = 1L;

    @Override
    public double calcularValorViagem(Veiculo veiculo, int duracaoMinutos) {
        int diferenca = duracaoMinutos - 15;
        return diferenca > 0 ? diferenca * veiculo.getTaxaMinuto() : 0;
    }

    @Override
    public String getNome() {
        return "Mensal";
    }

    @Override
    public String toString() {
        return getNome();
    }
}
