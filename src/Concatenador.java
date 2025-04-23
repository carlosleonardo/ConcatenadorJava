import org.apache.commons.cli.*;

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
            CommandLine cmd = obterLinhaComando(args, analisador, options);
            if (cmd == null) return;

            // Verifica se o arquivo de destino é um diretório
            File arquivoDestino = new File(cmd.getOptionValue("d"));
            if (arquivoDestino.isDirectory()) {
                System.out.println("O arquivo de destino não pode ser um diretório");
                return;
            }
            // Verifica se o arquivo de origem é um diretório
            for (String arquivoOrigem : cmd.getOptionValues("o")) {
                File arquivo = new File(arquivoOrigem);
                if (arquivo.isDirectory()) {
                    System.out.println("O arquivo de origem não pode ser um diretório: " + arquivoOrigem);
                    return;
                }
            }
            // Verifica se o arquivo de destino já existe
            if (arquivoDestino.exists()) {
                System.out.println("O arquivo de destino já existe. Deseja sobrescrever? (s/n)");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String resposta = reader.readLine();
                if (!resposta.equalsIgnoreCase("s")) {
                    System.out.println("Operação cancelada.");
                    return;
                }
            }


            // Extrai os argumentos informados
            System.out.println("Concatenando arquivos...");
            concatenaArquivos(cmd.getOptionValue("d"), cmd.getOptionValues("o"));
        } catch (Exception e) {
            System.out.println("Erro ao processar os argumentos: " + e.getMessage());
            
        }


    }

    private static CommandLine obterLinhaComando(String[] args, DefaultParser analisador, Options options) throws ParseException {
        CommandLine cmd = analisador.parse(options, args);
        if (cmd.hasOption("h") || args.length == 0) {
            System.out.println("Ajuda");
            System.out.println("Uso: java Concatenador -d <arquivo de destino> -o <arquivos de origem> -h");
            return null;
        }
        // Verifica se o arquivo de destino foi informado
        if (!cmd.hasOption("d")) {
            System.out.println("Informe o arquivo de destino");
            return null;
        }
        // Verifica se o arquivo de origem foi informado
        if (!cmd.hasOption("o")) {
            System.out.println("Informe os arquivos de origem");
            return null;
        }
        return cmd;
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
