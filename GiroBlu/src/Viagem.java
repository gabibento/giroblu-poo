
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Viagem {

    private Usuario usuario;
    private Veiculo veiculo;

    private Estacao estacaoOrigem;
    private Estacao estacaoDestino;

    private LocalDate dataInicio;
    private LocalTime horaInicio;

    private LocalDate dataFim;
    private LocalTime horaFim;

    private int duracaoMinutos;
    private double valor;

    public Viagem(Usuario usuario, Veiculo veiculo, Estacao estacaoOrigem,
                  LocalDate data, LocalTime hora) {
        this.usuario = usuario;
        this.veiculo = veiculo;
        this.estacaoOrigem = estacaoOrigem;
        this.dataInicio = data;
        this.horaInicio = hora;
    }

    public void encerrarViagem(Estacao estacao, LocalDate data, LocalTime hora) {

        if (dataFim != null) {
            throw new IllegalStateException("Esta viagem já foi encerrada");
        }

        if (estacao == null || data == null || hora == null) {
            throw new IllegalArgumentException("Dados de encerramento inválidos");
        }

        LocalDateTime inicio = LocalDateTime.of(dataInicio, horaInicio);
        LocalDateTime fim = LocalDateTime.of(data, hora);

        if (!fim.isAfter(inicio)) {
            throw new IllegalArgumentException(
                    "A data e o horário de encerramento devem ser posteriores ao início"
            );
        }

        if (estacao.getVagasDisponiveis() <= 0) {
            throw new IllegalStateException("Estação sem vagas disponíveis");
        }

        duracaoMinutos = (int) Duration.between(inicio, fim).toMinutes();

        valor = usuario.getPlano().calcularValorViagem(veiculo, duracaoMinutos);
        valor = Math.round(valor * 100.0) / 100.0;

        dataFim = data;
        horaFim = hora;
        estacaoDestino = estacao;

        veiculo.finalizarUso(duracaoMinutos);
        estacaoDestino.adicionarVeiculo(veiculo);

        usuario.getCarteiraCreditos().debitar(this);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Estacao getEstacaoOrigem() {
        return estacaoOrigem;
    }

    public Estacao getEstacaoDestino() {
        return estacaoDestino;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public double getValor() {
        return valor;
    }
}
