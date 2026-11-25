package model;

import java.io.Serializable;

import model.exceptions.GiroBluException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CarteiraCreditos implements Serializable {

    private static final long serialVersionUID = 1L;

    private double saldo;
    private List<Recarga> recargas;

    public CarteiraCreditos() {
        this.saldo = 0;
        this.recargas = new ArrayList<>();
    }

    public void adicionarRecarga(Recarga recarga) {
        if (recarga == null) {
            throw new GiroBluException("Recarga deve ser informada");
        }
        saldo += recarga.getValor();
        recargas.add(recarga);
    }

    protected void debitar(Viagem viagem) {
        saldo -= viagem.getValor();
    }

    public List<LancamentoExtrato> gerarExtrato(List<Viagem> viagens) {

        List<LancamentoExtrato> lancamentos = new ArrayList<>();

        for (Recarga recarga : recargas) {

            LocalDateTime dataHora = LocalDateTime.of(recarga.getData(), recarga.getHora());
            LancamentoExtrato lancamento = new LancamentoExtrato(dataHora, "Recarga", recarga.getValor());

            lancamentos.add(lancamento);
        }

        for (Viagem viagem : viagens) {

            LocalDateTime dataHora = LocalDateTime.of(viagem.getDataFim(), viagem.getHoraFim());
            LancamentoExtrato lancamento = new LancamentoExtrato(dataHora, "Viagem - " + viagem.getVeiculo().getModelo(), -viagem.getValor());

            lancamentos.add(lancamento);
        }

        lancamentos.sort(Comparator.comparing(LancamentoExtrato::getDataHora));

        double saldoAcumulado = 0;

        for (LancamentoExtrato lancamento : lancamentos) {
            saldoAcumulado += lancamento.getValor();

            lancamento.setSaldoAcumulado(Math.round(saldoAcumulado * 100.0) / 100.0);
        }

        return lancamentos;
    }

    public double getSaldo() {
        return saldo;
    }

}
