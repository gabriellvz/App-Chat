package protocolo;

// clase responsavel por definir metodos para o menu inicial do chat
public class UI {

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
        for (int i=0; i<nEspacos; i++){
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
}
