import java.util.List;

public class CarteiraCreditos {
    private double saldo;
    private List<Recarga> recargas;

    public CarteiraCreditos(double saldo, List<Recarga> recargas) {
        this.saldo = saldo;
        this.recargas = recargas;
    }

    public void adicionarRecarga(Recarga recarga) {
        saldo += recarga.getValor();
        recargas.add(recarga);
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Recarga> getRecargas() {
        return recargas;
    }
}
