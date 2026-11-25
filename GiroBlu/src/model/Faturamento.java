package model;

import model.exceptions.GiroBluException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Faturamento {

    private final LocalDate inicio;
    private final LocalDate fim;

    private final Map<String, Integer> quantidadePorTipo = new LinkedHashMap<>();
    private final Map<String, Double> valorPorTipo = new LinkedHashMap<>();

    private int quantidadeTotal;
    private double valorTotal;

    public Faturamento(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) {
            throw new GiroBluException("Informe a data inicial e a data final");
        }
        if (inicio.isAfter(fim)) {
            throw new GiroBluException("A data inicial deve ser anterior ou igual à data final");
        }
        this.inicio = inicio;
        this.fim = fim;
    }

    void registrarViagem(Viagem viagem) {

        if (viagem == null || viagem.getDataFim() == null) {
            throw new IllegalArgumentException("Viagem deve ser informada");
        }

        if (viagem.getDataFim().isBefore(inicio) || viagem.getDataFim().isAfter(fim)) {
            throw new IllegalArgumentException("Viagem fora do período");
        }

        String tipo = viagem.getVeiculo().getTipo();
        quantidadePorTipo.put(tipo, quantidadePorTipo.getOrDefault(tipo, 0) + 1);

        valorPorTipo.put(tipo, arredondar(valorPorTipo.getOrDefault(tipo, 0.0) + viagem.getValor()));

        quantidadeTotal++;
        valorTotal = arredondar(valorTotal + viagem.getValor());
    }

    private static double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public Map<String, Integer> getQuantidadePorTipo() {
        return Collections.unmodifiableMap(quantidadePorTipo);
    }

    public Map<String, Double> getValorPorTipo() {
        return Collections.unmodifiableMap(valorPorTipo);
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}