package model;

import java.io.*;

public class ArquivoGiroBlu {

    public static final String CAMINHO_PADRAO = "dados/giroblu.dat";

    private final File arquivo;

    public ArquivoGiroBlu() {
        this(CAMINHO_PADRAO);
    }

    public ArquivoGiroBlu(String caminho) {
        this.arquivo = new File(caminho);
    }

    public void gravar(SistemaGiroBlu giroBlu) throws IOException {

        File pasta = arquivo.getParentFile();
        if (pasta != null) {
            pasta.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(arquivo)))) {
            oos.writeObject(giroBlu);
        }
    }

    public SistemaGiroBlu ler() throws IOException, ClassNotFoundException {
        if (!arquivo.exists()) {
            return new SistemaGiroBlu();
        }


        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(arquivo)))) {
            return (SistemaGiroBlu) ois.readObject();
        }
    }

}
