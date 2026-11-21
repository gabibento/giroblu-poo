public abstract class VeiculoEletrico extends Veiculo {

    private int bateria;

    public VeiculoEletrico(String codigo, String modelo, Estacao estacao, String status) {
        super(codigo, modelo, estacao, status);
        bateria = 100;
    }

    @Override
    public boolean podeSerLiberado() {
        return getStatus().equals(StatusVeiculo.DISPONIVEL) && bateria >= 20;
    }

    @Override
    public void liberarVeiculo() {
        if (bateria < 20) {
            throw new IllegalStateException("Bateria fraca: " + bateria + "%");
        }

    }

}
