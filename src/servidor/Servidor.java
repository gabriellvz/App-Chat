//package clienteServidor;

package servidor;

//import cliente.ClienteThread;

//import protocolo.TipoMensagem;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import protocolo.TipoMensagem;

public class Servidor {

	// lista estatica para armazenar clientes conectados ao servidor
	static ArrayList<ServidorThread> clientesConectados = new ArrayList<>();

	// metodo responsavel por validar o comando digitado pelo usuario
	public static void verificarComando (ServidorThread clienteConectado, String mensagem){

		String [] partes = mensagem.split(" ", 2); // divide a mensagem por espacos " " em duas partes
		String nomeUsuario = clienteConectado.getNomeUsuario();

		// "/" caracter que marca um comando
		if (mensagem.startsWith("/")){
			
			if(partes[0].startsWith("/crs@")) { //verifica se eh uma mensagem privada
				String destinatario = partes[0].substring(5); //corta os cinco primeiros caracteres pra pegar apenas o nome do destinatario
				
				//verfificar se o cliente nao digitou nada(tamanho de partes<2) ou se ele digitou apenas espacos em branco
				if(partes.length < 2 || partes[1].trim().isEmpty()) {
					clienteConectado.enviarMensagem(TipoMensagem.ERRO+" Digite uma mensagem apos o nome.");
				}
				else {
					//tenta enviar a mensagem
					boolean mensagemChegou = enviarMensagemPrivada(nomeUsuario,destinatario,partes[1]);
					
					if(mensagemChegou) { //se a mensagem chegou ele confirma pro rementente
						clienteConectado.enviarMensagem(TipoMensagem.PRIVADO+"voce para <" + destinatario + "> " + partes[1]);
					}
					else { //se a mensagem nao chegou ele confirma pro rementente
						clienteConectado.enviarMensagem(TipoMensagem.ERRO + destinatario +" nao encontrado");
					}
				}
			}else {// se nao for uma mensagem privada continua procurando nos outro comandos
				switch (partes[0]){

				case "/listar":
					clienteConectado.enviarMensagem(listarUsuariosConectados()); // tem que ser so para o cliente
					break;

				case "/msg":
					if (partes.length < 2 || partes[1].trim().isEmpty()){ // trim remove espacos, is empty verifica se o tamanho eh 0
						clienteConectado.enviarMensagem(TipoMensagem.ERRO + "Digite uma mensagem apos /msg.");
					}
					else{
						broadcast(TipoMensagem.GERAL + "\u001B[33m<" + nomeUsuario + ">\u001B[0m " + partes[1]); // envia mensagem para broadcast
					}
					break;

				case "/sair":
					clienteConectado.desconectar(); //usa o metodo de ServidorThread para mudar o estado da variavel conectado
					break;
					
				// a mensagem tiver o marcador de comando '/' mas nao for um comando valido
				default:
					clienteConectado.enviarMensagem(TipoMensagem.ERRO + "Digite um comando valido.");
					break;
			  }		
		   }
	    }
	    // se a mensagem nao for um comando
		else {
			clienteConectado.enviarMensagem(TipoMensagem.ERRO + "Digite um comando valido.");
	    }
	}

	// metodo responsavel por listar nomes dos usuarios conectados
	public static String listarUsuariosConectados(){
		StringBuilder sb = new StringBuilder(); // stringBuilder serve para formatar saidas convertidas para string
		int i = 0; // indice
		sb.append("\n" + TipoMensagem.LISTA + "Usuarios conectados: " + "\n");
		for (ServidorThread c : clientesConectados){
			i++;
			sb.append(i + ". " + c.getNomeUsuario()+ "\n");
		}
		return sb.toString(); // retorna um objeto sb que converte a saida para string
	}

	// metodo responsavel por retornar um boolean para saber se um nome ja esta presente na lista
	public static boolean nomeJaExiste (String nome){
		for (ServidorThread c : clientesConectados){
			if (nome.equalsIgnoreCase(c.getNomeUsuario())){ // equalsIgnoreCase compara desconsiderando entre maiusculas e minusculas
				return true;
			}
		}
		return false;
	}

	public static void adicionar(ServidorThread s){
		clientesConectados.add(s);
	}

	public static void remover(ServidorThread s){
		clientesConectados.remove(s);
	}

	// metodo responsavel por enviar a mensagem para todos clientes conectados
	// o metodo percorre a lista de clientes conectados e envia uma mensagem a todas as trheads ativas
	public static void broadcast (String mensagem){
		for (ServidorThread c : clientesConectados){
			c.enviarMensagem(mensagem);
		}
	}
	
	public static boolean enviarMensagemPrivada(String remetente,String destinatario,String mensagem) {
		for(ServidorThread cliente : clientesConectados) {//percorre a lista de clientes conectados e envia mensagem somente pro que tiver o nome correspondente
			if(cliente.getNomeUsuario().equalsIgnoreCase(destinatario)) {
				cliente.enviarMensagem(TipoMensagem.PRIVADO + remetente+" "+mensagem);
				return true;
			}
		}return false;//se nao encontrar ninguem retorna falso
	}

	public static boolean sairDoChat() {
		return false;
		
	}

	public static void main(String[] args) {
        try {
        	ServerSocket serverSocket = new ServerSocket(4000);//inicia um servidor de rede e abre a porta 4000 para se conectar com um cliente
    		while(true) {

    			System.out.println(TipoMensagem.SERVIDOR + "aguardando conexao...");
        		
        		Socket socket = serverSocket.accept();//pausa a execucao, espera um cliente se conectar e retorna um socket

        		ServidorThread servidorThread = new ServidorThread(socket);//cria uma thread responsavel por um cliente
        		servidorThread.start();

        		
        		System.out.println("\u001B[32m" + TipoMensagem.SERVIDOR + "um cliente se conectou.\u001B[0m");

        		//serverSocket.close(); criar um if para fechar a conexao
    		}		
		}catch(IOException ex) {
			ex.printStackTrace();
			
		}	
	}
}
