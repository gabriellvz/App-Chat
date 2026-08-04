package protocolo;

// clase responsavel por definir metodos utilitarios para o menu inicial do chat
public class UI {

    // codigos ANSI para diferentes cores
    private static final String RESETAR_COR = "\u001B[0m"; //codigo para resetar estilos
    private static final String COR_VERMELHO = "\u001B[31m";
    private static final String COR_VERDE = "\u001B[32m";
    private static final String COR_AMARELO = "\u001B[33m";
    private static final String COR_AZUL = "\u001B[34m";
    private static final String COR_CIANO = "\u001B[36m";

    //imprimeLinha
    private static void imprimirLinha(){
        for (int i=0; i < 73; i++){
            System.out.print("-");
        }
        System.out.println();
    }

    // imprime barra
    private static void imprimirBarra(){
        System.out.println();
        for (int i=0; i < 77; i++){
            System.out.print("=");
        }
        System.out.println();
    }

    // imprime espacos
    private static void imprimirEspacos(int nEspacos){
        for (int i=0; i < nEspacos; i++){
            System.out.print(" ");
        }
    }

    // imprime comandos
    private static void imprimirComandos(){
        System.out.printf("%n[Comandos  disponiveis]:%n%n");
        imprimirEspacos(4);
        imprimirLinha();
        System.out.printf("    %-12s - %s%n", "/listar","Lista os usuarios conectados ao servidor.");
        System.out.printf("    %-12s - %s%n", "/msg", "Envia uma mensagem.");
        System.out.printf("    %-12s - %s%n", "/crs@", "Cria uma sala de chat privado com outro usuario.");
        System.out.printf("    %-12s - %s%n", "/sair", "O usario sera desconectado do chat.");
        imprimirEspacos(4);
        imprimirLinha();
    }

    // imprime instrucoes
    private static void imprimirInstrucoes(){
        System.out.printf("%n[Instrucoes adicionais]:%n%n");
        imprimirEspacos(4);
        imprimirLinha();
        System.out.println("    1. Para o comando /msg utilize o formato: /msg_mensagem");
        System.out.println("    2. Para o comando /crs@ utilize o formato: /crs@nomeUsuario");
        imprimirEspacos(4);
        imprimirLinha();
    }

    // calcula a quantidade de espacos totais para centralizar o texto
    // 77 = largura total to titulo
    // ex: 24 = tamanho do texto
    // 77 - 24 = 53
    // 53 / 2 para alinhar ao centro dos espaco que sobrou ao subtrair o tamanho total pelo tamanho do texto
    private static int formatar(String textoTitulo){
        int tamanho = textoTitulo.length();
        return (77 - tamanho) / 2;
    }

    // imprime titulo
    private static void imprimirTitulo(){
        String titulo = "Bem vindo ao zap zap 2.0";
        imprimirBarra();
        imprimirEspacos(formatar(titulo));
        System.out.print(titulo);
        imprimirBarra();
    }

    // imprime menu
    public static void imprimirMenu(){
        imprimirTitulo();
        imprimirComandos();
        imprimirInstrucoes();
        System.out.println();
    }

    public static String estilizarMensagem (char cor ,String mensagem){
        switch (cor){
            case 'R':
                return UI.COR_VERMELHO + mensagem + UI.RESETAR_COR;

            case 'Y':
                return UI.COR_AMARELO +  mensagem  + UI.RESETAR_COR;

            case 'G':
                return UI.COR_VERDE + mensagem + UI.RESETAR_COR;

            case 'B':
                return UI.COR_AZUL + mensagem + UI.RESETAR_COR;

            case 'C':
                return UI.COR_CIANO + mensagem + UI.RESETAR_COR;

        }
        return mensagem;
    }

}
