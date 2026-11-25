package model;

import model.exceptions.GiroBluException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SistemaGiroBlu {

    private List<Estacao> estacoes = new ArrayList<>();
    private List<Veiculo> veiculos = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();

    // ================= Req. 1: cadastros =================

    public Estacao cadastrarEstacao(String nome, int vagas) {

        for (Estacao e : estacoes) {
            if (e.getNome().equalsIgnoreCase(nome)) {
                throw new GiroBluException("Estação chamada " + nome + " já existente");
            }
        }

        Estacao estacao = new Estacao(nome, vagas);
        estacoes.add(estacao);
        return estacao;
    }

    public Veiculo cadastrarBicicletaMecanica(String codigo, String modelo, String nomeEstacao) {
        validarCodigoVeiculo(codigo);
        Veiculo bicicletaMecanica = new BicicletaMecanica(codigo, modelo, buscarEstacao(nomeEstacao), StatusVeiculo.DISPONIVEL);
        veiculos.add(bicicletaMecanica);
        return bicicletaMecanica;
    }


    public Veiculo cadastrarBicicletaEletrica(String codigo, String modelo, String nomeEstacao) {
        validarCodigoVeiculo(codigo);
        Veiculo bicicletaEletrica = new BicicletaEletrica(codigo, modelo, buscarEstacao(nomeEstacao), StatusVeiculo.DISPONIVEL);
        veiculos.add(bicicletaEletrica);
        return bicicletaEletrica;
    }

    public Veiculo cadastrarPatineteEletrico(String codigo, String modelo, String nomeEstacao) {
        validarCodigoVeiculo(codigo);
        Veiculo patineteEletrico = new PatineteEletrico(codigo, modelo, buscarEstacao(nomeEstacao), StatusVeiculo.DISPONIVEL);
        veiculos.add(patineteEletrico);
        return patineteEletrico;
    }

    private void validarCodigoVeiculo(String codigo) {
        if (buscarVeiculoPorCodigo(codigo) != null) {
            throw new GiroBluException("Veículo de código já cadastrado");
        }
    }

    public Usuario cadastrarUsuario(String nome, String email, Plano plano) {

        if (buscarUsuarioPorEmail(email) != null) {
            throw new GiroBluException("Email já cadastrado");
        }

        Usuario usuario = new Usuario(nome, email, plano);
        usuarios.add(usuario);
        return usuario;
    }

    // ================= Buscas =================

    public Estacao buscarEstacao(String nome) {

        for (Estacao e : estacoes) {
            if (e.getNome().equalsIgnoreCase(nome)) {
                return e;
            }
        }
        throw new GiroBluException("Estação não encontrada");
    }

    public Veiculo buscarVeiculo(String codigo) {
        Veiculo veiculo = buscarVeiculoPorCodigo(codigo);
        if (veiculo == null) {
            throw new GiroBluException("Veículo não encontrado");
        }
        return veiculo;
    }

    public Usuario buscarUsuario(String email) {
        Usuario usuario = buscarUsuarioPorEmail(email);

        if(usuario == null) {
            throw new GiroBluException("Usuário não encontrado");
        }
        return usuario;
    }


    // ================= Listagens (somente leitura) =================

    public List<Estacao> listarEstacoes() {
        return Collections.unmodifiableList(estacoes);
    }

    public List<Veiculo> listarVeiculos() {
        return Collections.unmodifiableList(veiculos);
    }

    public List<Usuario> listarUsuarios() {
        return Collections.unmodifiableList(usuarios);
    }

    private Veiculo buscarVeiculoPorCodigo(String codigo) {
        for (Veiculo v : veiculos) {
            if (v.getCodigo().equals(codigo)) {
                return v;
            }
        }
        return null;
    }

    private Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario u : usuarios) {
            if(u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    public void registrarRecarga(String email, LocalDate data, LocalTime hora, double valor) {
        buscarUsuario(email).getCarteiraCreditos().adicionarRecarga(new Recarga(data, hora, valor));
    }

    public void iniciarViagem(String email, String codigoVeiculo, String nomeEstacao, LocalDate data, LocalTime hora) {
      buscarUsuario(email).iniciarViagem(buscarVeiculo(codigoVeiculo), buscarEstacao(nomeEstacao), data, hora);
    }

    public Viagem encerrarViagem(String email, String nomeEstacaoDestino, LocalDate data, LocalTime hora) {
        return buscarUsuario(email).encerrarViagem(buscarEstacao(nomeEstacaoDestino), data, hora);
    }

    // ================= Req. 5: manutenção e bateria =================

    public void enviarParaManutencao(String codigo) {
        buscarVeiculo(codigo).enviarParaManutencao();
    }

    public void devolverManutencao(String codigo) {
        buscarVeiculo(codigo).devolverManutencao();
    }

    public void recarregarBateria(String codigo) {
        buscarVeiculo(codigo).recarregarBateria();
    }

    // ================= Req. 6: veículos disponíveis =================

    public List<Veiculo> consultarVeiculosDisponiveis(String nomeEstacao) {
        return buscarEstacao(nomeEstacao).listarVeiculosDisponiveis();
    }

    // ================= Req. 7: extrato =================

    public List<LancamentoExtrato> gerarExtrato(String email) {
        return buscarUsuario(email).getCarteiraCreditos().gerarExtrato();
    }

    // ================= Req. 8: faturamento =================

    public Faturamento consultarFaturamento(LocalDate inicio, LocalDate fim) {
        Faturamento faturamento = new Faturamento(inicio, fim);

        for (Usuario u : usuarios) {
            for (Viagem v : u.getViagensConcluidas()) {
                LocalDate dataFim = v.getDataFim();
                if (!dataFim.isBefore(inicio) && !dataFim.isAfter(fim)) {
                    faturamento.registrarViagem(v);
                }
            }
        }

        return faturamento;
    }
}