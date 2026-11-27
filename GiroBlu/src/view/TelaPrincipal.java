package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import model.ArquivoGiroBlu;
import model.SistemaGiroBlu;

public class TelaPrincipal extends JFrame {

    private JTabbedPane abas = new JTabbedPane();
    private JTextArea txaResultado = new JTextArea(12, 70);

    private ArquivoGiroBlu arquivo = new ArquivoGiroBlu();
    private SistemaGiroBlu sistema;

    public TelaPrincipal() {
        super("GiroBlu");

        carregarDados();
        abas.addTab("Cadastros", new PainelCadastros(this));
        abas.addTab("Viagens", new PainelViagens(this));
        abas.addTab("Frota", new PainelFrota(this));
        abas.addTab("Consultas", new PainelConsultas(this));

        txaResultado.setEditable(false);
        txaResultado.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        setLayout(new BorderLayout(10, 10));
        add(abas, BorderLayout.CENTER);
        add(new JScrollPane(txaResultado), BorderLayout.SOUTH);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Salvar ao ENCERRAR: windowClosing roda antes do EXIT_ON_CLOSE fechar o programa
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salvarDados();
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Recupera os dados gravados. Na primeira execução o arquivo não existe
     * e o sistema começa vazio.
     *
     * Se o arquivo existir mas não puder ser lido, o programa é encerrado:
     * continuar com um sistema vazio faria o próximo salvamento apagar os dados.
     */
    private void carregarDados() {
        try {
            sistema = arquivo.ler();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Não foi possível carregar os dados:\n" + e.getMessage()
                            + "\n\nO programa será fechado para não sobrescrever o arquivo.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Grava todos os dados no arquivo. Os painéis chamam este método
     * depois de cada operação que alterou dados.
     */
    public void salvarDados() {
        try {
            arquivo.gravar(sistema);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os dados:\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Mostra um texto na área de resultado, substituindo o anterior.
     *
     * @param texto texto a ser exibido
     */
    public void mostrarResultado(String texto) {
        txaResultado.setText(texto);
        txaResultado.setCaretPosition(0); // volta a rolagem para o início
    }

    /**
     * @return o sistema com os dados carregados, usado pelos painéis
     */
    public SistemaGiroBlu getSistema() {
        return sistema;
    }

    public static void main(String[] args) {
        new TelaPrincipal().setVisible(true);
    }
}
