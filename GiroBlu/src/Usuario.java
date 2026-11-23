import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private String email;
    private Plano plano;
    private CarteiraCreditos carteiraCreditos;
    private Viagem viagemAndamento;
    private List<Viagem> viagensConcluidas;

    public Usuario(String nome, String email, Plano plano, CarteiraCreditos carteiraCreditos) {
        this.nome = nome;
        this.email = email;
        this.plano = plano;
        this.carteiraCreditos = carteiraCreditos;
        viagensConcluidas = new ArrayList<>();
    }


    public void iniciarViagem(Veiculo veiculo, Estacao estacao) {

        if (viagemAndamento != null) {
            throw new IllegalStateException("Você já possui uma viagem em andamento");
        }

        if (carteiraCreditos.getSaldo() < 5) {
            throw new IllegalStateException("Saldo insuficiente");
        }

        if (veiculo == null || estacao == null) {
            throw new IllegalArgumentException("Veículo ou estação inválidos");
        }

        if (veiculo.getEstacao() != estacao) {
            throw new IllegalStateException("O veículo não pertence a esta estação");
        }

        if (!veiculo.podeSerLiberado()) {
            throw new IllegalStateException("Veículo indisponível no momento");
        }

        estacao.removerVeiculo(veiculo);
        veiculo.liberar();

        viagemAndamento = new Viagem(this, veiculo, estacao, LocalDate.now(), LocalTime.now());
    }


    public void encerrarViagem(Estacao estacao) {

        if (viagemAndamento == null) {
            throw new IllegalStateException("Não existe viagem em andamento");
        }

        if (estacao == null) {
            throw new IllegalArgumentException("Estação de destino inválida");
        }

        viagemAndamento.encerrarViagem(estacao, LocalDate.now(), LocalTime.now());
        viagensConcluidas.add(viagemAndamento);
        viagemAndamento = null;
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
        return viagensConcluidas;
    }
}
