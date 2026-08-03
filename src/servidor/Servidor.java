//package clienteServidor;

package servidor;

//import cliente.ClienteThread;

import protocolo.TipoMensagem;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Servidor {


	// lista estatica para armazenar clientes conectados ao servidor
	static ArrayList<ServidorThread> clientesConectados = new ArrayList<>();


	// metodo responsavel por validar o comando digitado pelo usuario
	public static boolean verificarComando (ServidorThread clienteConectado, String mensagem){

		String [] partes = mensagem.split(" ", 2); // divide a mensagem por espacos " " em duas partes
		String nomeUsuario = clienteConectado.getNomeUsuario();

		// "/" caracter que marca um comando
		if (mensagem.startsWith("/")){

			switch (partes[0]){

				case "/listar":
					clienteConectado.enviarMensagem(listarUsuariosConectados()); // tem que ser so para o cliente
					break;

				case "/msg":
					if (partes.length < 2 || partes[1].trim().isEmpty()){ // trim remove espacos, is empty verifica se o tamanho eh 0
						clienteConectado.enviarMensagem(TipoMensagem.ERRO + "Digite uma mensagem apos /msg.");
					}
					else{
						broadcast(TipoMensagem.GERAL + "<" + nomeUsuario + "> " + partes[1]); // envia mensagem para broadcast
					}
					break;

				case "/sair":
					return false; //para parar de receber mensagens

				// a mensagem tiver o marcador de comando '/' mas nao for um comando valido
				default:
					clienteConectado.enviarMensagem(TipoMensagem.ERRO + "Digite um comando valido.");
					break;
			}

		}
		// se a mensagem nao for um comando
		else {
			clienteConectado.enviarMensagem(TipoMensagem.ERRO + "Digite um comando valido.");
		}
		return true;//retorna verdadeiro pra continuar recebendo mensagens


	}


	// metodo responsavel por listar nomes dos usuarios conectados
	public static String listarUsuariosConectados(){
		StringBuilder sb = new StringBuilder(); // stringBuilder serve para formatar saidas convertidas para string
		int i = 0; // indice
		sb.append("\n" + TipoMensagem.LISTA + "Usuarios conectados: " + "\n");
		for (ServidorThread c : clientesConectados){
			i++;
			sb.append(i + ". " + c.getNomeUsuario() + "\n");
			//System.out.println(i + ". " + c.getNomeUsuario());
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

	// classe responsavel por enviar a mensagem para todos clientes conectados
	// o metodo percorre a lista de clientes conectados e envia uma mensagem a todas as trheads ativas
	public static void broadcast (String mensagem){
		for (ServidorThread c : clientesConectados){
			c.enviarMensagem(mensagem);
		}
	}


	public static void main(String[] args) {
        try {
        	ServerSocket serverSocket = new ServerSocket(4000);//inicia um servidor de rede e abre a porta 4000 para se conectar com um cliente
    		while(true) {

    			System.out.println(TipoMensagem.SERVIDOR + "aguardando conexao...");
        		
        		Socket socket = serverSocket.accept();//pausa a execucao, espera um cliente se conectar e retorna um socket

        		ServidorThread servidorThread = new ServidorThread(socket);//cria uma thread responsavel por um cliente
        		servidorThread.start();

        		
        		System.out.println(TipoMensagem.SERVIDOR + "um cliente se conectou.");

        		//serverSocket.close(); criar um if para fechar a conexao
    		}		
		}catch(IOException ex) {
			ex.printStackTrace();
			
		}	
	}
}
