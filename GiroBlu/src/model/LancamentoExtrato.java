package model;

import java.time.LocalDateTime;

public class LancamentoExtrato {
    private LocalDateTime dataHora;
    private String descricao;
    private double valor;
    private double saldoAcumulado;

    public LancamentoExtrato(LocalDateTime dataHora, String descricao, double valor) {
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.valor = valor;
    }

    public boolean isCredito() {
        return valor > 0;
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

    public double getSaldoAcumulado() {
        return saldoAcumulado;
    }

    protected void setSaldoAcumulado(double saldoAcumulado) {
        this.saldoAcumulado = saldoAcumulado;
    }
}