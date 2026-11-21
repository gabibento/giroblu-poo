import java.sql.Time;
import java.util.Date;

public class Recarga {
    private Date data;
    private Time hora;
    private double valor;

    public Recarga(Date data, Time hora, double valor) {
        if(valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }

        this.data = data;
        this.hora = hora;
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public Time getHora() {
        return hora;
    }

    public double getValor() {
        return valor;
    }
}
