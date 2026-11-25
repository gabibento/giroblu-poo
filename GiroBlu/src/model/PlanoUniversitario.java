package model;

public class PlanoUniversitario implements Plano {

    @Override
    public double calcularValorViagem(Veiculo veiculo, int duracaoMinutos) {
        double valorCheio = veiculo.getTaxaDesbloqueio() + veiculo.getTaxaMinuto() * duracaoMinutos;

        return valorCheio - (valorCheio) * veiculo.getDescontoUniversitario();
    }
}
