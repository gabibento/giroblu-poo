package model;

import java.io.Serializable;

import model.exceptions.GiroBluException;
import model.exceptions.VeiculoIndisponivelException;

public abstract class Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private String modelo;
    private Estacao estacao;

    private StatusVeiculo status;

    public Veiculo(String codigo, String modelo, Estacao estacao, StatusVeiculo status) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new GiroBluException("Código do veículo obrigatório");
        }
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new GiroBluException("Modelo do veículo obrigatório");
        }
        if (status == null) {
            throw new GiroBluException("Status do veículo obrigatório");
        }

        this.codigo = codigo;
        this.modelo = modelo;
        this.estacao = estacao;
        this.status = status;

        if (status != StatusVeiculo.EM_USO) {
            if (estacao == null) {
                throw new GiroBluException("Estação do veículo não informada");
            }
            estacao.adicionarVeiculo(this);
        }
    }

    protected void liberar() {
        if (!podeSerLiberado()) {
            throw new VeiculoIndisponivelException();
        }

        this.status = StatusVeiculo.EM_USO;
    }

    protected void finalizarUso(int duracaoMinutos) {

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
            throw new GiroBluException("Não é possível enviar um veículo em uso para manutenção");
        }

        if (status == StatusVeiculo.EM_MANUTENCAO) {
            throw new GiroBluException("Veículo já está em manutenção");
        }

        status = StatusVeiculo.EM_MANUTENCAO;
    }

    public void devolverManutencao() {

        if (status != StatusVeiculo.EM_MANUTENCAO) {
            throw new GiroBluException("O veículo não está em manutenção");
        }
        status = StatusVeiculo.DISPONIVEL;
    }

    public abstract boolean podeSerLiberado();


    public void recarregarBateria() {
        throw new GiroBluException("Este veículo não possui bateria");
    }

    public String descricao() {
        return "Código: " + getCodigo() + " | Tipo: " + getTipo() + " | Modelo: " + getModelo();
    }


    public String getCodigo() {
        return codigo;
    }

    public String getModelo() {
        return modelo;
    }

    protected void setEstacao(Estacao estacao) {
        this.estacao = estacao;
    }



    public Estacao getEstacao() {
        return estacao;
    }


    public StatusVeiculo getStatus() {
        return status;
    }

    public abstract String getTipo();

    public abstract double getTaxaDesbloqueio();

    public abstract double getTaxaMinuto();

    public abstract double getDescontoUniversitario();


}
