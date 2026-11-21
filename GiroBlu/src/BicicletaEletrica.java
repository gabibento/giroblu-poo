public class BicicletaEletrica extends VeiculoEletrico {

    private static double TAXA_DESBLOQUEIO = 2.0;
    private static double TAXA_MINUTO = 0.30;

    public BicicletaEletrica(String codigo, String modelo, Estacao estacao, String status) {
        super(codigo, modelo, estacao, status);
    }

    @Override
    public double getDescontoUniversitario() {
        return 0.50;
    }
}
