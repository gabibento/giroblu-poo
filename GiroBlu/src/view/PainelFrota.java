package view;

import model.*;
import model.exceptions.GiroBluException;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

public class PainelFrota extends JPanel {

    private TelaPrincipal tela;

    private JTextField txtCodigoVeiculo = new JTextField(8);
    private JButton btnEnviarManutencao = new JButton("Enviar para manutenção");
    private JButton btnDevolverManutencao = new JButton("Devolver à operação");
    private JButton btnRecarregarBateria = new JButton("Recarregar bateria");

    private JTextField txtEstacao = new JTextField(12);
    private JButton btnConsultarDisponiveis = new JButton("Ver veículos disponíveis");

    public PainelFrota(TelaPrincipal tela) {
        this.tela = tela;

        JPanel camposVeiculo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        camposVeiculo.add(new JLabel("Código do veículo:"));
        camposVeiculo.add(txtCodigoVeiculo);

        JPanel botoesVeiculo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoesVeiculo.add(btnEnviarManutencao);
        botoesVeiculo.add(btnDevolverManutencao);
        botoesVeiculo.add(btnRecarregarBateria);

        JPanel grupoVeiculo = criarGrupo("Manutenção e bateria", camposVeiculo, botoesVeiculo);

        JPanel camposEstacao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        camposEstacao.add(new JLabel("Estação:"));
        camposEstacao.add(txtEstacao);

        JPanel botoesEstacao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoesEstacao.add(btnConsultarDisponiveis);

        JPanel grupoEstacao = criarGrupo("Veículos disponíveis", camposEstacao, botoesEstacao);

        setLayout(new GridLayout(3, 1, 10, 10));
        add(grupoVeiculo);
        add(grupoEstacao);

        btnEnviarManutencao.addActionListener(e -> enviarParaManutencao());
        btnDevolverManutencao.addActionListener(e -> devolverManutencao());
        btnRecarregarBateria.addActionListener(e -> recarregarBateria());
        btnConsultarDisponiveis.addActionListener(e -> consultarDisponiveis());
    }

    private JPanel criarGrupo(String titulo, JPanel campos, JPanel botoes) {
        JPanel grupo = new JPanel(new GridLayout(2, 1));
        grupo.setBorder(BorderFactory.createTitledBorder(titulo));
        grupo.add(campos);
        grupo.add(botoes);
        return grupo;
    }

    private void enviarParaManutencao() {
        try {
            String codigo = txtCodigoVeiculo.getText().trim();
            tela.getSistema().enviarParaManutencao(codigo);

            tela.mostrarResultado("Veículo enviado para manutenção: " + tela.getSistema().buscarVeiculo(codigo).descricao());
            tela.salvarDados();
            txtCodigoVeiculo.setText("");

        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void devolverManutencao() {
        try {
            String codigo = txtCodigoVeiculo.getText().trim();
            tela.getSistema().devolverManutencao(codigo);

            tela.mostrarResultado("Veículo devolvido à operação: " + tela.getSistema().buscarVeiculo(codigo).descricao());
            tela.salvarDados();
            txtCodigoVeiculo.setText("");

        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void recarregarBateria() {
        try {
            String codigo = txtCodigoVeiculo.getText().trim();
            tela.getSistema().recarregarBateria(codigo);

            tela.mostrarResultado("Bateria recarregada: " + tela.getSistema().buscarVeiculo(codigo).descricao());
            tela.salvarDados();
            txtCodigoVeiculo.setText("");

        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void consultarDisponiveis() {
        try {
            Estacao estacao = tela.getSistema().buscarEstacao(txtEstacao.getText().trim());
            List<Veiculo> disponiveis = tela.getSistema().consultarVeiculosDisponiveis(estacao.getNome());

            StringBuilder texto = new StringBuilder();
            texto.append("Veículos disponíveis na estação ").append(estacao.getNome())
                    .append(" (").append(estacao.getVagasDisponiveis()).append(" vaga(s) livre(s)):\n\n");

            if (disponiveis.isEmpty()) {
                texto.append("(nenhum veículo disponível)");
            }
            for (Veiculo veiculo : disponiveis) {
                texto.append(veiculo.descricao()).append("\n");
            }

            tela.mostrarResultado(texto.toString());

        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }
    }
}
