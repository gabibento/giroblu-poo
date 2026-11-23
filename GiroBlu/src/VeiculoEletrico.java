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
    public void finalizarUso(int duracaoMinutos) {
        super.finalizarUso(duracaoMinutos);

        int consumo = duracaoMinutos * getConsumoPorMinuto();

        this.bateria -= consumo;

        if (this.bateria < 0) {
            this.bateria = 0;
        }
    }

    public void recarregarBateria() {

        if (getStatus() == StatusVeiculo.EM_USO) {
            throw new IllegalStateException("Não é possível recarregar um veículo em uso");
        }
        bateria = 100;
    }

    protected abstract int getConsumoPorMinuto();

}
