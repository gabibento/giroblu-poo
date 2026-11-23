
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CarteiraCreditos {
    private double saldo;
    private double saldoInicial;
    private List<Recarga> recargas;
    private List<Viagem> viagens;

    public CarteiraCreditos(double saldo) {
        this.saldo = saldo;
        this.saldoInicial = saldo;
        this.recargas = new ArrayList<>();
        this.viagens = new ArrayList<>();
    }

    public void adicionarRecarga(Recarga recarga) {
        saldo += recarga.getValor();
        recargas.add(recarga);
    }

    public void debitar(Viagem viagem) {
        saldo -= viagem.getValor();
        viagens.add(viagem);
    }

    public List<LancamentoExtrato> gerarExtrato() {

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

        double saldoAcumulado = saldoInicial;

        for (LancamentoExtrato lancamento : lancamentos) {
            saldoAcumulado += lancamento.getValor();

            lancamento.setSaldoApos(Math.round(saldoAcumulado * 100.0) / 100.0);
        }

        return lancamentos;
    }

    public double getSaldo() {
        return saldo;
    }

}
