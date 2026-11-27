package view;

import model.*;
import model.exceptions.GiroBluException;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class PainelViagens extends JPanel {

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private TelaPrincipal tela;

    private JTextField txtEmailRecarga = new JTextField(15);
    private JTextField txtValorRecarga = new JTextField(6);
    private JTextField txtDataRecarga = new JTextField(dataAtual(), 8);
    private JTextField txtHoraRecarga = new JTextField(horaAtual(), 4);
    private JButton btnRegistrarRecarga = new JButton("Registrar recarga");

    private JTextField txtEmailInicio = new JTextField(15);
    private JTextField txtCodigoVeiculo = new JTextField(8);
    private JTextField txtEstacaoInicio = new JTextField(12);
    private JTextField txtDataInicio = new JTextField(dataAtual(), 8);
    private JTextField txtHoraInicio = new JTextField(horaAtual(), 4);
    private JButton btnIniciarViagem = new JButton("Iniciar viagem");

    private JTextField txtEmailFim = new JTextField(15);
    private JTextField txtEstacaoFim = new JTextField(12);
    private JTextField txtDataFim = new JTextField(dataAtual(), 8);
    private JTextField txtHoraFim = new JTextField(horaAtual(), 4);
    private JButton btnEncerrarViagem = new JButton("Encerrar viagem");

    public PainelViagens(TelaPrincipal tela) {
        this.tela = tela;

        JPanel camposRecarga = new JPanel(new FlowLayout(FlowLayout.LEFT));
        camposRecarga.add(new JLabel("E-mail:"));
        camposRecarga.add(txtEmailRecarga);
        camposRecarga.add(new JLabel("Valor (R$):"));
        camposRecarga.add(txtValorRecarga);
        camposRecarga.add(new JLabel("Data:"));
        camposRecarga.add(txtDataRecarga);
        camposRecarga.add(new JLabel("Hora:"));
        camposRecarga.add(txtHoraRecarga);

        JPanel botoesRecarga = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoesRecarga.add(btnRegistrarRecarga);

        JPanel grupoRecarga = criarGrupo("Recarga", camposRecarga, botoesRecarga);

        JPanel camposInicio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        camposInicio.add(new JLabel("E-mail:"));
        camposInicio.add(txtEmailInicio);
        camposInicio.add(new JLabel("Veículo:"));
        camposInicio.add(txtCodigoVeiculo);
        camposInicio.add(new JLabel("Estação:"));
        camposInicio.add(txtEstacaoInicio);
        camposInicio.add(new JLabel("Data:"));
        camposInicio.add(txtDataInicio);
        camposInicio.add(new JLabel("Hora:"));
        camposInicio.add(txtHoraInicio);

        JPanel botoesInicio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoesInicio.add(btnIniciarViagem);

        JPanel grupoInicio = criarGrupo("Iniciar viagem", camposInicio, botoesInicio);

        JPanel camposFim = new JPanel(new FlowLayout(FlowLayout.LEFT));
        camposFim.add(new JLabel("E-mail:"));
        camposFim.add(txtEmailFim);
        camposFim.add(new JLabel("Estação de destino:"));
        camposFim.add(txtEstacaoFim);
        camposFim.add(new JLabel("Data:"));
        camposFim.add(txtDataFim);
        camposFim.add(new JLabel("Hora:"));
        camposFim.add(txtHoraFim);

        JPanel botoesFim = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoesFim.add(btnEncerrarViagem);

        JPanel grupoFim = criarGrupo("Encerrar viagem", camposFim, botoesFim);

        setLayout(new GridLayout(3, 1, 10, 10));
        add(grupoRecarga);
        add(grupoInicio);
        add(grupoFim);

        btnRegistrarRecarga.addActionListener(e -> registrarRecarga());
        btnIniciarViagem.addActionListener(e -> iniciarViagem());
        btnEncerrarViagem.addActionListener(e -> encerrarViagem());
    }

    private JPanel criarGrupo(String titulo, JPanel campos, JPanel botoes) {
        JPanel grupo = new JPanel(new GridLayout(2, 1));
        grupo.setBorder(BorderFactory.createTitledBorder(titulo));
        grupo.add(campos);
        grupo.add(botoes);
        return grupo;
    }

    private static String dataAtual() {
        return LocalDate.now().format(FORMATO_DATA);
    }

    private static String horaAtual() {
        return LocalTime.now().format(FORMATO_HORA);
    }

    private void registrarRecarga() {
        try {
            String email = txtEmailRecarga.getText().trim();
            double valor = Double.parseDouble(txtValorRecarga.getText().trim().replace(',', '.'));
            LocalDate data = LocalDate.parse(txtDataRecarga.getText().trim(), FORMATO_DATA);
            LocalTime hora = LocalTime.parse(txtHoraRecarga.getText().trim(), FORMATO_HORA);

            tela.getSistema().registrarRecarga(email, data, hora, valor);
            double saldo = tela.getSistema().buscarUsuario(email).getCarteiraCreditos().getSaldo();

            tela.mostrarResultado("Recarga de R$ " + String.format("%.2f", valor) + " registrada para " + email
                    + "\nSaldo atual: R$ " + String.format("%.2f", saldo));
            tela.salvarDados();
            txtEmailRecarga.setText("");
            txtValorRecarga.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor deve ser um número.",
                    "Dado inválido", JOptionPane.WARNING_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data ou hora inválida. Use dd/mm/aaaa e hh:mm.",
                    "Dado inválido", JOptionPane.WARNING_MESSAGE);
        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void iniciarViagem() {
        try {
            String email = txtEmailInicio.getText().trim();
            String codigo = txtCodigoVeiculo.getText().trim();
            String estacao = txtEstacaoInicio.getText().trim();
            LocalDate data = LocalDate.parse(txtDataInicio.getText().trim(), FORMATO_DATA);
            LocalTime hora = LocalTime.parse(txtHoraInicio.getText().trim(), FORMATO_HORA);

            tela.getSistema().iniciarViagem(email, codigo, estacao, data, hora);

            tela.mostrarResultado("Viagem iniciada: " + email + " com o veículo " + codigo
                    + " na estação " + estacao + ", em " + data.format(FORMATO_DATA) + " às " + hora.format(FORMATO_HORA));
            tela.salvarDados();
            txtEmailInicio.setText("");
            txtCodigoVeiculo.setText("");
            txtEstacaoInicio.setText("");

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data ou hora inválida. Use dd/mm/aaaa e hh:mm.",
                    "Dado inválido", JOptionPane.WARNING_MESSAGE);
        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void encerrarViagem() {
        try {
            String email = txtEmailFim.getText().trim();
            String estacao = txtEstacaoFim.getText().trim();
            LocalDate data = LocalDate.parse(txtDataFim.getText().trim(), FORMATO_DATA);
            LocalTime hora = LocalTime.parse(txtHoraFim.getText().trim(), FORMATO_HORA);

            Viagem viagem = tela.getSistema().encerrarViagem(email, estacao, data, hora);

            tela.mostrarResultado("Viagem encerrada na estação " + viagem.getEstacaoDestino().getNome()
                    + "\nDuração: " + viagem.getDuracaoMinutos() + " min"
                    + "\nValor cobrado: R$ " + String.format("%.2f", viagem.getValor())
                    + "\nVeículo: " + viagem.getVeiculo().descricao());
            tela.salvarDados();
            txtEmailFim.setText("");
            txtEstacaoFim.setText("");

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data ou hora inválida. Use dd/mm/aaaa e hh:mm.",
                    "Dado inválido", JOptionPane.WARNING_MESSAGE);
        } catch (GiroBluException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Operação não permitida", JOptionPane.WARNING_MESSAGE);
        }
    }
}
