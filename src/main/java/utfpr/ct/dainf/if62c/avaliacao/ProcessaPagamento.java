package utfpr.ct.dainf.if62c.avaliacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IF62C Fundamentos de Programação 2 Avaliação parcial.
 *
 * @author Tomás Abril  (tomas.abril@gmail.com)
 */
public class ProcessaPagamento {

    private BufferedReader reader;

    public ProcessaPagamento(File arquivo) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(arquivo));
    }

    public ProcessaPagamento(String path) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(path));
    }

    private String getNextLine() throws IOException {
        return reader.readLine();
    }

    private Credor processaLinha(String linha) {
        Long cpf;
        String nome = "";
        Double valor;
        Date data;

        String cpfS = "";
        for (int i = 0; i <= 10; i++) {
            cpfS = cpfS + linha.charAt(i);
        }
        cpf = Long.valueOf(cpfS);

        for (int i = 11; i <= 70; i++) {
            nome = nome + linha.charAt(i);
        }
        nome = nome.trim();

        String valorS = "";
        for (int i = 71; i <= 82; i++) {
            valorS = valorS + linha.charAt(i);
        }
        valor = Double.valueOf(valorS);

        String dataAno = "";
        String dataMes = "";
        String dataDia = "";
        for (int i = 83; i <= 86; i++) {
            dataAno = dataAno + linha.charAt(i);
        }
        for (int i = 87; i <= 88; i++) {
            dataMes = dataMes + linha.charAt(i);
        }
        for (int i = 89; i <= 90; i++) {
            dataDia = dataDia + linha.charAt(i);
        }
        Calendar calendario = new GregorianCalendar();

        calendario.set(Integer.valueOf(dataAno), Integer.valueOf(dataMes), Integer.valueOf(dataDia), 0, 0, 0);
        data = new Date(calendario.getTimeInMillis());
        Credor credor = new Credor(cpf, nome, valor, data);

        return credor;
    }

    private Credor getNextCredor() throws IOException {
        String linha = getNextLine();
        if (linha == null) {
            return null;
        }
        Credor nextCredor = processaLinha(linha);
        return nextCredor;
    }

    public List<Credor> getCredorList() throws IOException {
        List<Credor> lista = new ArrayList<>();
        Credor credor = getNextCredor();
        while (credor != null) {
            lista.add(credor);
            credor = getNextCredor();
        }
        //Sorting cpf
        Collections.sort(lista, new Comparator<Credor>() {
            @Override
            public int compare(Credor o1, Credor o2) {
                return o1.compareTo(o2);
            }
        });
        reader.close();
        return lista;
    }

    public Map<Long, Credor> getCredorMap() throws IOException {
        Map<Long, Credor> mapa = new HashMap<>();
        Credor credor = getNextCredor();
        while (credor != null) {
            mapa.put(credor.getCpf(), credor);
            credor = getNextCredor();
        }

        reader.close();
        return mapa;
    }
}
