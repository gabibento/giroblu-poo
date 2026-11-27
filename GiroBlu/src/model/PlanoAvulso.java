package model;

public class PlanoAvulso implements Plano {

    private static final long serialVersionUID = 1L;

    @Override
    public double calcularValorViagem(Veiculo veiculo, int duracaoMinutos) {
        return veiculo.getTaxaDesbloqueio() + veiculo.getTaxaMinuto() * duracaoMinutos;
    }

    @Override
    public String getNome() {
        return "Avulso";
    }

    @Override
    public String toString() {
        return getNome();
    }
}
