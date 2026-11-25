package model;

public class PlanoAvulso implements Plano {

    @Override
    public double calcularValorViagem(Veiculo veiculo, int duracaoMinutos) {
        return veiculo.getTaxaDesbloqueio() + veiculo.getTaxaMinuto() * duracaoMinutos;
    }

    @Override
    public String getNome() {
        return "Avulso";
    }
}
