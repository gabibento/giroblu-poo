package view;

import model.*;
import model.exceptions.GiroBluException;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;

/**
 * CAMADA DE APRESENTAÇÃO
 *
 * Aba de cadastros (requisito 1): estações, veículos e usuários.
 *
 * Organização do painel (GridLayout de 3 linhas e 1 coluna):
 *   cada linha é um grupo com borda e título (Estação, Veículo, Usuário),
 *   e cada grupo tem uma linha de campos e uma linha de botões.
 */
public class PainelCadastros extends JPanel {

    private TelaPrincipal tela;

    // ---------- Estação ----------
    private JTextField txtNomeEstacao = new JTextField(15);
    private JTextField txtVagas = new JTextField(5);
    private JButton btnCadastrarEstacao = new JButton("Cadastrar estação");

    // ---------- Veículo ----------
    private JTextField txtCodigoVeiculo = new JTextField(8);
    private JTextField txtModeloVeiculo = new JTextField(12);
    private JTextField txtEstacaoVeiculo = new JTextField(12);
    private JButton btnBicicletaMecanica = new JButton("Cadastrar bicicleta mecânica");
    private JButton btnBicicletaEletrica = new JButton("Cadastrar bicicleta elétrica");
    private JButton btnPatineteEletrico = new JButton("Cadastrar patinete elétrico");

    // ---------- Usuário ----------
    private JTextField txtNomeUsuario = new JTextField(15);
    private JTextField txtEmailUsuario = new JTextField(15);
    private JComboBox<Plano> cbPlano = new JComboBox<>(new Plano[] {new PlanoAvulso(), new PlanoMensal(), new PlanoUniversitario()});
    private JButton btnCadastrarUsuario = new JButton("Cadastrar usuário");

    public PainelCadastros(TelaPrincipal tela) {
        this.tela = tela;

        // ---------- grupo Estação ----------
        JPanel camposEstacao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        camposEstacao.add(new JLabel("Nome:"));
        camposEstacao.add(txtNomeEstacao);
        camposEstacao.add(new JLabel("Vagas:"));
        camposEstacao.add(txtVagas);

        JPanel botoesEstacao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoesEstacao.add(btnCadastrarEstacao);

        JPanel grupoEstacao = criarGrupo("Estação", camposEstacao, botoesEstacao);

        // ---------- grupo Veículo ----------
        JPanel camposVeiculo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        camposVeiculo.add(new JLabel("Código:"));
        camposVeiculo.add(txtCodigoVeiculo);
        camposVeiculo.add(new JLabel("Modelo:"));
        camposVeiculo.add(txtModeloVeiculo);
        camposVeiculo.add(new JLabel("Estação:"));
        camposVeiculo.add(txtEstacaoVeiculo);

        JPanel botoesVeiculo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoesVeiculo.add(btnBicicletaMecanica);
        botoesVeiculo.add(btnBicicletaEletrica);
        botoesVeiculo.add(btnPatineteEletrico);

        JPanel grupoVeiculo = criarGrupo("Veículo", camposVeiculo, botoesVeiculo);

        // ---------- grupo Usuário ----------
        JPanel camposUsuario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        camposUsuario.add(new JLabel("Nome:"));
        camposUsuario.add(txtNomeUsuario);
        camposUsuario.add(new JLabel("E-mail:"));
        camposUsuario.add(txtEmailUsuario);
        camposUsuario.add(new JLabel("Plano:"));
        camposUsuario.add(cbPlano);

        JPanel botoesUsuario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoesUsuario.add(btnCadastrarUsuario);

        JPanel grupoUsuario = criarGrupo("Usuário", camposUsuario, botoesUsuario);

        // ---------- painel: os três grupos empilhados ----------
        // GridLayout(linhas, colunas, espaço horizontal, espaço vertical)
        setLayout(new GridLayout(3, 1, 10, 10));
        add(grupoEstacao);
        add(grupoVeiculo);
        add(grupoUsuario);

        // ---------- Ouvintes ----------
        btnCadastrarEstacao.addActionListener(e -> cadastrarEstacao());
        btnBicicletaMecanica.addActionListener(e -> cadastrarBicicletaMecanica());
        btnBicicletaEletrica.addActionListener(e -> cadastrarBicicletaEletrica());
        btnPatineteEletrico.addActionListener(e -> cadastrarPatineteEletrico());
        btnCadastrarUsuario.addActionListener(e -> cadastrarUsuario());
    }

    /**
     * Monta um grupo com borda e título: a linha de campos em cima
     * e a linha de botões embaixo.
     */
    private JPanel criarGrupo(String titulo, JPanel campos, JPanel botoes) {
        JPanel grupo = new JPanel(new GridLayout(2, 1));
        grupo.setBorder(BorderFactory.createTitledBorder(titulo));
        grupo.add(campos);
        grupo.add(botoes);
        return grupo;
    }

    private void cadastrarEstacao() {
        try {
            String nome = txtNomeEstacao.getText().trim();
            int vagas = Integer.parseInt(txtVagas.getText().trim());
            Estacao estacao = tela.getSistema().cadastrarEstacao(nome, vagas);

            tela.mostrarResultado("Estação cadastrada: " + estacao.getNome()
                    + " (" + estacao.getCapacidade() + " vagas)");
            tela.salvarDados();
            txtNomeEstacao.setText("");
            txtVagas.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vagas deve ser um número inteiro.",
                    "Dado inválido", JOptionPane.WARNING_MESSAGE);
        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cadastrarBicicletaMecanica() {
        try {
            Veiculo bicicletaMecanica = tela.getSistema().cadastrarBicicletaMecanica(
                    txtCodigoVeiculo.getText().trim(),
                    txtModeloVeiculo.getText().trim(),
                    txtEstacaoVeiculo.getText().trim());

            tela.mostrarResultado("Bicicleta mecânica cadastrada: " + bicicletaMecanica.descricao());
            tela.salvarDados();
            txtCodigoVeiculo.setText("");
            txtModeloVeiculo.setText("");
            txtEstacaoVeiculo.setText("");

        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void cadastrarBicicletaEletrica() {
        try {
            Veiculo bicicletaEletrica = tela.getSistema().cadastrarBicicletaEletrica(
                    txtCodigoVeiculo.getText().trim(),
                    txtModeloVeiculo.getText().trim(),
                    txtEstacaoVeiculo.getText().trim());

            tela.mostrarResultado("Bicicleta elétrica cadastrada: " + bicicletaEletrica.descricao());
            tela.salvarDados();
            txtCodigoVeiculo.setText("");
            txtModeloVeiculo.setText("");
            txtEstacaoVeiculo.setText("");

        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cadastrarPatineteEletrico() {
        try {
            Veiculo patineteEletrico = tela.getSistema().cadastrarPatineteEletrico(
                    txtCodigoVeiculo.getText().trim(),
                    txtModeloVeiculo.getText().trim(),
                    txtEstacaoVeiculo.getText().trim());

            tela.mostrarResultado("Patinete elétrico cadastrado: " + patineteEletrico.descricao());
            tela.salvarDados();
            txtCodigoVeiculo.setText("");
            txtModeloVeiculo.setText("");
            txtEstacaoVeiculo.setText("");

        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cadastrarUsuario() {
        try {
            Usuario usuario = tela.getSistema().cadastrarUsuario(
                    txtNomeUsuario.getText().trim(),
                    txtEmailUsuario.getText().trim(),
                    (Plano) cbPlano.getSelectedItem());

            tela.mostrarResultado("Usuário cadastrado: " + usuario.getNome() + " (" + usuario.getEmail() + ") - plano " + usuario.getPlano().getNome());
            tela.salvarDados();
            txtNomeUsuario.setText("");
            txtEmailUsuario.setText("");

        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }
    }
}
