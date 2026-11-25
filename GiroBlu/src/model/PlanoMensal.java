package model;

public class PlanoMensal implements Plano {
    @Override
    public double calcularValorViagem(Veiculo veiculo, int duracaoMinutos) {
        int diferenca = duracaoMinutos - 15;
        return diferenca > 0 ? diferenca * veiculo.getTaxaMinuto() : 0;
    }

    @Override
    public String getNome() {
        return "Mensal";
    }
}
