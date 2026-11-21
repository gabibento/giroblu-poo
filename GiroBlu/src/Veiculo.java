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


    public double getTaxaDesbloqueio() {
        return TAXA_DESBLOQUEIO;
    }

    public double getTaxaMinuto() {
        return TAXA_MINUTO;
    }

    public abstract double getDescontoUniversitario();
}
