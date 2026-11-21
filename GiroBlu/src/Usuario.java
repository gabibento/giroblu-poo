import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
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
    }

    public void iniciarViagem(Veiculo veiculo, Estacao estacao) {
        if (viagemAndamento != null) {
            throw new IllegalStateException("Você já possui uma viagem em andamento");
        }

        if (carteiraCreditos.getSaldo() < 5) {
            throw new IllegalStateException("Saldo insuficiente");
        }

        if (!veiculo.podeSerLiberado()) {
            throw new IllegalStateException("Veículo indisponível no momento");
        }

        veiculo.liberar();
        viagemAndamento = new Viagem(this, veiculo, estacao, LocalDate.now(), LocalTime.now());
    }
}
