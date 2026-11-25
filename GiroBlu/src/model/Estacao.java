package model;

import java.io.Serializable;

import model.exceptions.GiroBluException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Estacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private int capacidade;
    private List<Veiculo> veiculos;

    public Estacao(String nome, int capacidade) {

        if (nome == null || nome.trim().isEmpty()) {
            throw new GiroBluException("Nome da estação obrigatório");
        }

        if (capacidade <= 0) {
            throw new GiroBluException("Capacidade da estação deve ser maior que zero vagas");
        }
        this.nome = nome;
        this.capacidade = capacidade;
        this.veiculos = new ArrayList<>();
    }

    protected void adicionarVeiculo(Veiculo veiculo) {

        if (veiculos.size() >= capacidade) {
            throw new GiroBluException("Estação sem vagas disponíveis");
        }

        if (veiculos.contains(veiculo)) {
            throw new IllegalStateException("Veículo já está nesta estação");
        }

        veiculos.add(veiculo);
        veiculo.setEstacao(this);
    }

    protected void removerVeiculo(Veiculo veiculo) {

        if (!veiculos.contains(veiculo)){
            throw new IllegalStateException("Veículo não encontrado nesta estação");
        }

        veiculos.remove(veiculo);
        veiculo.setEstacao(null);
    }

    public int getVagasDisponiveis() {
        return capacidade - veiculos.size();
    }

    public List<Veiculo> listarVeiculosDisponiveis() {
        List<Veiculo> disponiveis = new ArrayList<>();

        for (Veiculo veiculo : veiculos) {
            if (veiculo.getStatus() == StatusVeiculo.DISPONIVEL) {
                disponiveis.add(veiculo);
            }
        }

        return disponiveis;
    }

    public List<Veiculo> getVeiculos() {
        return Collections.unmodifiableList(veiculos);
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }


}