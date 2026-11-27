package view;

import model.*;
import model.exceptions.GiroBluException;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Map;

public class PainelConsultas extends JPanel {

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm");

    private TelaPrincipal tela;

    private JTextField txtEmailExtrato = new JTextField(15);
    private JButton btnEmitirExtrato = new JButton("Emitir extrato");

    private JTextField txtDataInicial = new JTextField(LocalDate.now().withDayOfMonth(1).format(FORMATO_DATA), 8);
    private JTextField txtDataFinal = new JTextField(LocalDate.now().format(FORMATO_DATA), 8);
    private JButton btnConsultarFaturamento = new JButton("Consultar faturamento");

    public PainelConsultas(TelaPrincipal tela) {
        this.tela = tela;

        JPanel camposExtrato = new JPanel(new FlowLayout(FlowLayout.LEFT));
        camposExtrato.add(new JLabel("E-mail do usuário:"));
        camposExtrato.add(txtEmailExtrato);

        JPanel botoesExtrato = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoesExtrato.add(btnEmitirExtrato);

        JPanel grupoExtrato = criarGrupo("Extrato do usuário", camposExtrato, botoesExtrato);

        JPanel camposFaturamento = new JPanel(new FlowLayout(FlowLayout.LEFT));
        camposFaturamento.add(new JLabel("Data inicial:"));
        camposFaturamento.add(txtDataInicial);
        camposFaturamento.add(new JLabel("Data final:"));
        camposFaturamento.add(txtDataFinal);

        JPanel botoesFaturamento = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoesFaturamento.add(btnConsultarFaturamento);

        JPanel grupoFaturamento = criarGrupo("Faturamento do período", camposFaturamento, botoesFaturamento);

        setLayout(new GridLayout(3, 1, 10, 10));
        add(grupoExtrato);
        add(grupoFaturamento);

        btnEmitirExtrato.addActionListener(e -> emitirExtrato());
        btnConsultarFaturamento.addActionListener(e -> consultarFaturamento());
    }

    private JPanel criarGrupo(String titulo, JPanel campos, JPanel botoes) {
        JPanel grupo = new JPanel(new GridLayout(2, 1));
        grupo.setBorder(BorderFactory.createTitledBorder(titulo));
        grupo.add(campos);
        grupo.add(botoes);
        return grupo;
    }

    private void emitirExtrato() {
        try {
            Usuario usuario = tela.getSistema().buscarUsuario(txtEmailExtrato.getText().trim());
            List<LancamentoExtrato> lancamentos = tela.getSistema().gerarExtrato(usuario.getEmail());

            StringBuilder texto = new StringBuilder();
            texto.append("Extrato de ").append(usuario.getNome())
                    .append(" (").append(usuario.getEmail()).append(") - plano ")
                    .append(usuario.getPlano().getNome()).append("\n\n");
            texto.append(String.format("%-16s  %-30s  %-7s  %10s  %10s%n",
                    "Data/hora", "Descrição", "Tipo", "Valor", "Saldo"));

            if (lancamentos.isEmpty()) {
                texto.append("(nenhum lançamento)\n");
            }
            for (LancamentoExtrato lancamento : lancamentos) {
                texto.append(String.format("%-16s  %-30s  %-7s  %10.2f  %10.2f%n",
                        lancamento.getDataHora().format(FORMATO_DATA_HORA),
                        lancamento.getDescricao(),
                        lancamento.isCredito() ? "Crédito" : "Débito",
                        Math.abs(lancamento.getValor()),
                        lancamento.getSaldoAcumulado()));
            }

            texto.append(String.format("%nSaldo atual: R$ %.2f", usuario.getCarteiraCreditos().getSaldo()));
            tela.mostrarResultado(texto.toString());

        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void consultarFaturamento() {
        try {
            LocalDate inicio = LocalDate.parse(txtDataInicial.getText().trim(), FORMATO_DATA);
            LocalDate fim = LocalDate.parse(txtDataFinal.getText().trim(), FORMATO_DATA);

            Faturamento faturamento = tela.getSistema().consultarFaturamento(inicio, fim);

            StringBuilder texto = new StringBuilder();
            texto.append("Faturamento de ").append(inicio.format(FORMATO_DATA))
                    .append(" a ").append(fim.format(FORMATO_DATA)).append("\n\n");
            texto.append(String.format("%-20s  %8s  %12s%n", "Tipo de veículo", "Viagens", "Valor (R$)"));

            Map<String, Integer> quantidades = faturamento.getQuantidadePorTipo();
            Map<String, Double> valores = faturamento.getValorPorTipo();

            if (quantidades.isEmpty()) {
                texto.append("(nenhuma viagem encerrada no período)\n");
            }
            for (String tipo : quantidades.keySet()) {
                texto.append(String.format("%-20s  %8d  %12.2f%n", tipo, quantidades.get(tipo), valores.get(tipo)));
            }

            texto.append(String.format("%n%-20s  %8d  %12.2f", "Total geral",
                    faturamento.getQuantidadeTotal(), faturamento.getValorTotal()));
            tela.mostrarResultado(texto.toString());

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use dd/mm/aaaa.",
                    "Dado inválido", JOptionPane.WARNING_MESSAGE);
        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }
    }
}
