package model;

public class BicicletaMecanica extends Veiculo {

    private final static double TAXA_DESBLOQUEIO = 1.0;
    private final static double TAXA_MINUTO = 0.15;

    public BicicletaMecanica(String codigo, String modelo, Estacao estacao, StatusVeiculo status) {
        super(codigo, modelo, estacao, status);
    }


    @Override
    public boolean podeSerLiberado() {
        return getStatus().equals(StatusVeiculo.DISPONIVEL);
    }

    @Override
    public String getTipo() {
        return "Bicicleta Mecânica";
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
        return 0.50;
    }
}
