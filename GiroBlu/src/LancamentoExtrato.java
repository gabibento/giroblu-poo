import java.time.LocalDateTime;

public class LancamentoExtrato {
    private LocalDateTime dataHora;
    private String descricao;
    private double valor;
    private double saldoApos;

    public LancamentoExtrato(LocalDateTime dataHora, String descricao, double valor) {
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public double getSaldoApos() {
        return saldoApos;
    }

    public void setSaldoApos(double saldoApos) {
        this.saldoApos = saldoApos;
    }
}