import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.*;

public class Concatenador {
    public static void main(String[] args) {

        Options options = new Options();
        options.addOption("d", true, "Arquivo de destino");
        options.addOption("h", false, "Ajuda");
        var opcaoOrigem = new Option("o", "origem", true, "Arquivos de origem");
        opcaoOrigem.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(opcaoOrigem);

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
            System.out.println("Concatenando arquivos...");
            concatenaArquivos(cmd.getOptionValue("d"), cmd.getOptionValues("o"));
        } catch (Exception e) {
            System.out.println("Erro ao processar os argumentos: " + e.getMessage());
            
        }


    }

    private static void concatenaArquivos(String destino, String[] origem) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destino, true))) {
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
