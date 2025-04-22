import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import java.io.*;

public class Concatenador {
    public static void main(String[] args) {
        // Exobe ps ár}a,etrps se m]ap fpr omfpr,adp éçp ,emps dois arquivos
        Options options = new Options();
        options.addOption("d", true, "Arquivo de destino");
        options.addOption("h", false, "Ajuda");
        options.addOption("o", true, "Arquivos de origem");

        var analisador = new DefaultParser();
        try {
            CommandLine cmd = analisador.parse(options, args);
            if (cmd.hasOption("h") || args.length == 0) {
                System.out.println("Ajuda");
                System.out.println("Uso: java Concatenador -d <arquivo de destino> -o <arquivos de origem> -h");
                return;
            }
            // Verifica se o arquivo de destino foi informado
            if (!cmd.hasOption("d")) {
                System.out.println("Informe o arquivo de destino");
                return;
            }
            // Verifica se o arquivo de origem foi informado
            if (!cmd.hasOption("o")) {
                System.out.println("Informe os arquivos de origem");
                return;
            }
            // Extrai os argumentos informados
            concatenaArquivos(cmd.getOptionValue("d"), cmd.getOptionValues("o"));
        } catch (Exception e) {
            System.out.println("Erro ao processar os argumentos: " + e.getMessage());
            return;
        }

        System.out.println("Concatenando arquivos...");

    }

    private static void concatenaArquivos(String destino, String[] origem) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destino))) {
            for (String arquivo : origem) {
                try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        writer.write(linha);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao ler o arquivo " + arquivo + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo " + destino + ": " + e.getMessage());
        }
    }

}
