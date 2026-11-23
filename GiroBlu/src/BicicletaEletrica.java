public class BicicletaEletrica extends VeiculoEletrico {

    private static double TAXA_DESBLOQUEIO = 2.0;
    private static double TAXA_MINUTO = 0.30;

    public BicicletaEletrica(String codigo, String modelo, Estacao estacao, String status) {
        super(codigo, modelo, estacao, status);
    }

    @Override
    public double getTaxaDesbloqueio() {
        return TAXA_DESBLOQUEIO;
    }

    @Override
    public double getTaxaMinuto() {
        return TAXA_MINUTO;
    }

    @Override
    protected int getConsumoPorMinuto() {
        return 1;
    }

    @Override
    public double getDescontoUniversitario() {
        return 0.50;
    }
}
