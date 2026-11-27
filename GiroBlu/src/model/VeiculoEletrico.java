package model;

import model.exceptions.GiroBluException;

public abstract class VeiculoEletrico extends Veiculo {

    private static final long serialVersionUID = 1L;

    private int bateria;

    public VeiculoEletrico(String codigo, String modelo, Estacao estacao, StatusVeiculo status) {
        super(codigo, modelo, estacao, status);
        bateria = 100;
    }

    @Override
    public boolean podeSerLiberado() {
        return getStatus().equals(StatusVeiculo.DISPONIVEL) && bateria >= 20;
    }

    @Override
    protected void finalizarUso(int duracaoMinutos) {
        super.finalizarUso(duracaoMinutos);

        int consumo = duracaoMinutos * getConsumoPorMinuto();

        this.bateria -= consumo;

        if (this.bateria < 0) {
            this.bateria = 0;
        }
    }

    @Override
    public void recarregarBateria() {

        if (getStatus() == StatusVeiculo.EM_USO) {
            throw new GiroBluException("Não é possível recarregar um veículo em uso");
        }
        bateria = 100;
    }

    @Override
    public String descricao() {
        return super.descricao() + " | Bateria: " + bateria + "%";
    }

    public int getBateria() {
        return bateria;
    }

    protected abstract int getConsumoPorMinuto();

}
