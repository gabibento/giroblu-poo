public class PlanoMensal implements Plano {
    @Override
    public double calcularValorViagem(Veiculo veiculo, int duracao) {
        int diferenca = duracao - 15;
        return diferenca > 0 ? diferenca * veiculo.getTaxaMinuto() : 0;
    }
}
