import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Viagem {

    private Usuario usuario;
    private Veiculo veiculo;
    private Estacao estacaoOrigem;
    private LocalDate data;
    private LocalTime hora;

    public Viagem(Usuario usuario, Veiculo veiculo, Estacao estacaoOrigem, LocalDate data, LocalTime hora) {
        this.usuario = usuario;
        this.veiculo = veiculo;
        this.estacaoOrigem = estacaoOrigem;
        this.data = data;
        this.hora = hora;
    }
}
