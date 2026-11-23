public abstract class Veiculo {
    private String codigo;
    private String modelo;
    private Estacao estacao;

    private StatusVeiculo status;
    private double TAXA_DESBLOQUEIO;
    private double TAXA_MINUTO;

    public Veiculo(String codigo, String modelo, Estacao estacao, String status) {
        this.codigo = codigo;
        this.modelo = modelo;
        this.estacao = estacao;
        this.status = StatusVeiculo.DISPONIVEL;
    }

    public void liberar() {
        if (!podeSerLiberado()) {
            throw new IllegalStateException("Veículo não pode ser liberado");
        }

        this.status = StatusVeiculo.EM_USO;
    }

    public void finalizarUso(int duracaoMinutos) {

        if (status != StatusVeiculo.EM_USO) {
            throw new IllegalStateException("Veículo não está em uso");
        }

        if (duracaoMinutos < 0) {
            throw new IllegalArgumentException("Duração inválida");
        }

        this.status = StatusVeiculo.DISPONIVEL;
    }

    public void enviarParaManutencao() {

        if (status == StatusVeiculo.EM_USO) {
            throw new IllegalStateException("Não é possível enviar um veículo em uso para manutenção");
        }

        if (status == StatusVeiculo.EM_MANUTENCAO) {
            throw new IllegalStateException("Veículo já está em manutenção");
        }

        status = StatusVeiculo.EM_MANUTENCAO;
    }

    public void devolverManutencao() {

        if (status != StatusVeiculo.EM_MANUTENCAO) {
            throw new IllegalStateException("O veículo não está em manutenção");
        }
        status = StatusVeiculo.DISPONIVEL;
    }

    public abstract boolean podeSerLiberado();

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Estacao getEstacao() {
        return estacao;
    }

    public void setEstacao(Estacao estacao) {
        this.estacao = estacao;
    }

    public StatusVeiculo getStatus() {
        return status;
    }


    public abstract double getTaxaDesbloqueio();

    public abstract double getTaxaMinuto();

    public abstract double getDescontoUniversitario();
}
