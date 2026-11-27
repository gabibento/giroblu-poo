package model;

public class PlanoUniversitario implements Plano {

    private static final long serialVersionUID = 1L;

    @Override
    public double calcularValorViagem(Veiculo veiculo, int duracaoMinutos) {
        double valorCheio = veiculo.getTaxaDesbloqueio() + veiculo.getTaxaMinuto() * duracaoMinutos;

        return valorCheio - (valorCheio) * veiculo.getDescontoUniversitario();
    }

    @Override
    public String getNome() {
        return "Universitário";
    }

    @Override
    public String toString() {
        return getNome();
    }
}
