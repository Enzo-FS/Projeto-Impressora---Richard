import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;

public class Main
{

    // Interface que representa a DLL, usando JNA
    public interface ImpressoraDLL extends Library {

        // Caminho completo para a DLL
        ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                "C:/Users/enzo.santos/Desktop/Java-Aluno Graduacao/Java-Aluno Graduacao/E1_Impressora01.dll",
                ImpressoraDLL.class
        );


        private static String lerArquivoComoString(String path) throws IOException {
            FileInputStream fis = new FileInputStream(path);
            byte[] data = fis.readAllBytes();
            fis.close();
            return new String(data, StandardCharsets.UTF_8);
        }


        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);
        int FechaConexaoImpressora();
        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);
        int Corte(int avanco);
        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);
        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);
        int AvancaPapel(int linhas);
        int StatusImpressora(int param);
        int AbreGavetaElgin();
        int AbreGaveta(int pino, int ti, int tf);
        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);
        int ModoPagina();
        int LimpaBufferModoPagina();
        int ImprimeModoPagina();
        int ModoPadrao();
        int PosicaoImpressaoHorizontal(int posicao);
        int PosicaoImpressaoVertical(int posicao);
        int ImprimeXMLSAT	(String dados, int param);
        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    }

    private static boolean conexaoAberta = false;
    private static int tipo;
    private static String modelo;
    private static String conexao;
    private static int parametro;

    private static final Scanner scanner = new Scanner(System.in);

    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    //1
    public static void configurarConexao() {
        if(!conexaoAberta){
            System.out.println("Tipo de conexão: ");
            System.out.println("(1) USB\n(2) RS232\n(3) TCP/IP\n(4) Bluetooth\n(5)Impressoras acopladas\n" +
                    "(Android)");
            int tipo = scanner.nextInt();

            System.out.println("Modelo:\nI7\nBK-T681");
            String modelo = scanner.next();

            System.out.println("Conexão:\nUSB - USB\nRS232 - COM2\nTCP/IP - 192.168.0.20\nBluetooth - AA:BB:CC:DD:EE:FF");
            String conexao = scanner.next();

            System.out.println("Parâmetro: (0 caso seja conexão USB)");
            int parametro = scanner.nextInt();


            System.out.println("Impressona configurada com sucesso!");

        }
    }

    //2
    public static void abrirConexao() {
        if(conexaoAberta){
            System.out.println("Conexão já está aberta!");
        }
        int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
        if(retorno == 0){
            conexaoAberta = true;
            System.out.println("Conexão abrida com sucesso!");

        }


    }
    // 3
    public static void impressaoTexto(){
        System.out.println("Digite o que quer imprimir");
        String dados = scanner.nextLine();

        System.out.println("Digite a posição do texto, '0' Esquerda, '1' Centro, '2' Direita");
        int posicao = scanner.nextInt();

        System.out.println("Digite o estilo do texto, '0' Fonte A, '1' Fonte B, '2' Sublinhado, '4' Modo Reverso, '8' Negrito ");
        int estilo = scanner.nextInt();

        System.out.println("Digite o tamanho do texto,Altura:\n '0' para 1 x na altura\n'1' para 2 x na altura\n '2' para 3 x na altura\n '3' para 4 x na altura\n '4' para 5 x na altura\n '5' para 6 x na altura\n '6' para 7 x na altura\n '7' para 8 x na altura\n Largura:\n '16' para 2x na largura\n '32' para 3 x na largura\n '48' para 4 x na largura\n '64' para 5x na largura\n '80' para 6 x na largura\n '96' para 7 x na largura\n '112' para 8 x na largura ");
        int tamanho = scanner.nextInt();

        int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto(dados, posicao, estilo, tamanho);
        if(retorno ==0){
            System.out.println("Imprimindo texto...");
        }
        else{System.out.println("Um erro ocorreu!");}

    }


    //4
    public static void imprimirQrcode(){
        System.out.println("Insira os dados: ");
        String dados = scanner.next();

        System.out.println("Tamanho: \n(Entre 1-6)");
        int tamanho = scanner.nextInt();

        System.out.println("Nível de correção:\n(1) 7%\n(2) 15%\n(3) 25%\n(4) 30%");
        int nivelCorrecao = scanner.nextInt();

        int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode(dados, tamanho, nivelCorrecao);
        if(retorno==0){
            System.out.println("Imprimindo QRCode...");
        }
        else{System.out.println("Ocorreu algum erro.");}

    }
    //5
    public static void ImpressaoCodigoBarras(){
        int codbar = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A012345678912", 100, 2, 3);

        if(codbar == 0){
            System.out.println("Impressão bem sucedida!");
        }
        else{
            System.out.println("Impressão mal sucedida");
        }


    }

    //10
    public static void SinalSonoro(){

    }

    //0
    public static void fecharConexao() {
        conexaoAberta = false;
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n*************************************************");
            System.out.println("**************** MENU IMPRESSORA *******************");
            System.out.println("*************************************************\n");

            System.out.println("1  - Configurar Conexao");
            System.out.println("2  - Abrir Conexao");
            System.out.println("3  - Impressao Texto");
            System.out.println("4  - Impressao QRCode");
            System.out.println("5  - Impressao Cod Barras");
            System.out.println("6  - Impressao XML SAT");
            System.out.println("7  - Impressao XML Canc SAT");
            System.out.println("8  - Avançar papel");
            System.out.println("9  - Abrir Gaveta");
            System.out.println("10 - Sinal Sonoro");
            System.out.println("0  - Fechar Conexao e Sair");
            System.out.println("--------------------------------------");

            String escolha = capturarEntrada("\nDigite a opção desejada: ");

            if (escolha.equals("0")) {
                fecharConexao();
                System.out.println("Conexão fechada com sucesso!");
                break;
            }

            switch (escolha) {
                case "1":
                    configurarConexao();
                    break;
                case "2":
                    abrirConexao();
                    break;
                case "3":
                    impressaoTexto();
                    //ImpressoraDLL.INSTANCE.Corte(3);
                    break;

                case "4":
                    imprimirQrcode();
                    break;
                case "5":
                    ImpressaoCodigoBarras();
                    break;

                case "6":
                    // --- IMPORTANTE ---
                    // Este trecho permite ao usuário escolher um arquivo XML no computador.
                    // Para funcionar, será necessário importar as classes de manipulação de arquivos e da interface gráfica:
                    // import java.io.*;                    // Para trabalhar com arquivos (ex: File, IOException)
                    // import javax.swing.*;                // Para usar o JFileChooser (janela de seleção de arquivos)
                    //
                    // A ideia: abrir uma janela para o usuário escolher o XML, ler o conteúdo do arquivo
                    // e enviar para a função que imprime o XML de cancelamento do SAT.
                    //
                    // >>> Os alunos deverão implementar as partes de leitura do arquivo (função lerArquivoComoString)
                    // e o controle de fluxo (switch/case, etc) conforme aprendido em aula.
                    break;
                case "7":
                    // --- IMPORTANTE ---
                    // Este trecho permite ao usuário escolher um arquivo XML no computador.
                    // Para funcionar, será necessário importar as classes de manipulação de arquivos e da interface gráfica:
                    // import java.io.*;                    // Para trabalhar com arquivos (ex: File, IOException)
                    // import javax.swing.*;                // Para usar o JFileChooser (janela de seleção de arquivos)
                    //
                    // A ideia: abrir uma janela para o usuário escolher o XML, ler o conteúdo do arquivo
                    // e enviar para a função que imprime o XML de cancelamento do SAT.
                    //
                    // >>> Os alunos deverão implementar as partes de leitura do arquivo (função lerArquivoComoString)
                    // e o controle de fluxo (switch/case, etc) conforme aprendido em aula.
                    break;

                case "8":
                    ImpressoraDLL.INSTANCE.Corte(1);
                    break;

                case "9":
                    break;

                case "10":


                    break;



                default:

                    break;

            }
            //SCANNER CLOSE
        }

    }



}