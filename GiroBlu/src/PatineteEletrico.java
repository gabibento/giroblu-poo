public class PatineteEletrico extends VeiculoEletrico {
    private static double TAXA_DESBLOQUEIO = 3.0;
    private static double TAXA_MINUTO = 0.40;

    @Override
    public double getDescontoUniversitario() {
        return 0;
    }
}
