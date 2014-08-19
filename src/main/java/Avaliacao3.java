
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
//import utfpr.ct.dainf.if62c.avaliacao.Credor;
import utfpr.ct.dainf.if62c.avaliacao.*;

/**
 * IF62C Fundamentos de Programação 2 Avaliação parcial.
 *
 * @author Tomás Abril (tomas.abril@gmail.com)
 */
public class Avaliacao3 {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // Obtem o caminho para o arquivo de teste dentro da estrutura de diretórios
        // da aplicação.
        String arquivo = ClassLoader.getSystemClassLoader().getResource("credores.txt").getFile();

        ProcessaPagamento processa = new ProcessaPagamento(arquivo);
        
        List<Credor> lista = processa.getCredorList();
        Collections.sort(lista, new CredorComparator());
        //BufferedWriter escrever = new BufferedWriter(new FileWriter("saida.csv"));
        for (Credor c : lista) {
            System.out.println(c);
        }

        ProcessaPagamento processa2 = new ProcessaPagamento(arquivo);
        Map<Long, Credor> mapa = processa2.getCredorMap();
        Scanner input = new Scanner(System.in);
        System.out.println("Digite o numero do cpf para imprimir o Credor(0 para terminar): ");
        Credor credor;
        String digitado;
        Long cpf;
        while (true) {
            digitado = input.next();
            if (isLong(digitado)) {
                cpf = Long.parseLong(digitado);
                break;
            } else {
                System.out.println("Por favor, informe um valor numérico");
            }
        }
        while (cpf != 0) {
            credor = mapa.get(cpf);
            if (credor == null) {
                System.out.println("CPF inexistente");
            } else {
                System.out.println(credor);
            }
            while (true) {
                digitado = input.next();
                if (isLong(digitado)) {
                    cpf = Long.parseLong(digitado);
                    break;
                } else {
                    System.out.println("Por favor, informe um valor numérico");
                }
            }
        }
    }

    public static boolean isLong(String str) {
        try {
            long d = Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
