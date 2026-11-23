public class PatineteEletrico extends VeiculoEletrico {
    private static double TAXA_DESBLOQUEIO = 3.0;
    private static double TAXA_MINUTO = 0.40;

    public PatineteEletrico(String codigo, String modelo, Estacao estacao, String status) {
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
    public double getDescontoUniversitario() {
        return 0;
    }

    @Override
    protected int getConsumoPorMinuto() {
        return 2;
    }
}
