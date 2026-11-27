package model;

public class BicicletaEletrica extends VeiculoEletrico {

    private static final long serialVersionUID = 1L;

    private final static double TAXA_DESBLOQUEIO = 2.0;
    private final static double TAXA_MINUTO = 0.30;

    public BicicletaEletrica(String codigo, String modelo, Estacao estacao, StatusVeiculo status) {
        super(codigo, modelo, estacao, status);
    }

    @Override
    public String getTipo() {
        return "Bicicleta Elétrica";
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
