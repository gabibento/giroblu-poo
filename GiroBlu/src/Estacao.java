import java.util.ArrayList;
import java.util.List;

public class Estacao {

    private String nome;
    private int capacidade;
    private List<Veiculo> veiculos;

    public Estacao(String nome, int capacidade) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.veiculos = new ArrayList<>();
    }

    public void adicionarVeiculo(Veiculo veiculo) {

        if (veiculos.size() >= capacidade) {
            throw new IllegalStateException("Estação sem vagas disponíveis");
        }

        if (veiculos.contains(veiculo)) {
            throw new IllegalStateException("Veículo já está nesta estação");
        }

        veiculos.add(veiculo);
        veiculo.setEstacao(this);
    }

    public void removerVeiculo(Veiculo veiculo) {

        if (!veiculos.contains(veiculo)){
            throw new IllegalStateException("Veículo não encontrado nesta estação");
        }

        veiculos.remove(veiculo);
        veiculo.setEstacao(null);
    }

    public int getVagasDisponiveis() {
        return capacidade - veiculos.size();
    }

    public List<Veiculo> listarVeiculosDisponiveis() {
        List<Veiculo> disponiveis = new ArrayList<>();

        for (Veiculo veiculo : veiculos) {
            if (veiculo.podeSerLiberado()) {
                disponiveis.add(veiculo);
            }
        }

        return disponiveis;
    }
}