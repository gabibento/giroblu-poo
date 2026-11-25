package model.exceptions;

public class VeiculoIndisponivelException extends GiroBluException {
    public VeiculoIndisponivelException(String mensagem) {
        super(mensagem);
    }

    public VeiculoIndisponivelException() {
        super("Veículo indisponível no momento");
    }
}
