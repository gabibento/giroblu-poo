package model;

import java.io.Serializable;

public interface Plano extends Serializable {

    double calcularValorViagem(Veiculo veiculo, int duracaoMinutos);

    String getNome();

}
