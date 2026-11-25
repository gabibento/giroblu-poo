package model;

import model.exceptions.GiroBluException;

import model.exceptions.VeiculoIndisponivelException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {
    private String nome;
    private String email;
    private Plano plano;
    private CarteiraCreditos carteiraCreditos;
    private Viagem viagemAndamento;
    private List<Viagem> viagensConcluidas;

    public Usuario(String nome, String email, Plano plano) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new GiroBluException("Nome do usuário obrigatório");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new GiroBluException("E-mail do usuário obrigatório");
        }
        if (plano == null) {
            throw new GiroBluException("Plano do usuário obrigatório");
        }
        this.nome = nome;
        this.email = email;
        this.plano = plano;
        this.carteiraCreditos = new CarteiraCreditos();
        viagensConcluidas = new ArrayList<>();
    }


    public void iniciarViagem(Veiculo veiculo, Estacao estacao, LocalDate data, LocalTime hora) {

        if (viagemAndamento != null) {
            throw new GiroBluException("Você já possui uma viagem em andamento");
        }

        if (carteiraCreditos.getSaldo() < 5) {
            throw new GiroBluException("Saldo insuficiente");
        }

        if (veiculo == null || estacao == null) {
            throw new GiroBluException("Veículo ou estação inválidos");
        }

        if (veiculo.getEstacao() != estacao) {
            throw new GiroBluException("O veículo não pertence a esta estação");
        }

        if (!veiculo.podeSerLiberado()) {
            throw new VeiculoIndisponivelException();
        }

        if (data == null || hora == null) {
            throw new GiroBluException("Data e hora de início obrigatórias");
        }

        estacao.removerVeiculo(veiculo);
        veiculo.liberar();

        viagemAndamento = new Viagem(this, veiculo, estacao, data, hora);
    }


    public Viagem encerrarViagem(Estacao estacao, LocalDate data, LocalTime hora) {

        if (viagemAndamento == null) {
            throw new GiroBluException("Não existe viagem em andamento");
        }

        if (estacao == null) {
            throw new GiroBluException("Estação de destino inválida");
        }

        viagemAndamento.encerrarViagem(estacao, data, hora);
        viagensConcluidas.add(viagemAndamento);
        Viagem viagemRetorno = viagemAndamento;
        viagemAndamento = null;

        return viagemRetorno;
    }

    public List<LancamentoExtrato> gerarExtrato() {
        return carteiraCreditos.gerarExtrato(viagensConcluidas);
    }



    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Plano getPlano() {
        return plano;
    }

    public CarteiraCreditos getCarteiraCreditos() {
        return carteiraCreditos;
    }

    public Viagem getViagemAndamento() {
        return viagemAndamento;
    }

    public List<Viagem> getViagensConcluidas() {
        return Collections.unmodifiableList(viagensConcluidas);
    }
}
