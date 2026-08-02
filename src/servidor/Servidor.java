package servidor;

import cliente.ClienteThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Servidor {

	static ArrayList<ServidorThread> clientesConectados = new ArrayList<>();


	public static void verificarComando (String mensagem){
		String [] partes = mensagem.split(" ", 3); // divide a mensagem

		if (partes[0].equals("/listar")) {
			listarUsuariosConectados();
		}
	}


	// metodo responsavel por listar nomes dos usuarios conectados
	public static void listarUsuariosConectados(){
		System.out.println("Usuarios conectados:");
		int i = 0;
		for (ServidorThread c : clientesConectados){
			i++;
			System.out.println(i + ". " + c.getNomeUsuario());
		}
	}


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

    			System.out.println("servidor aguardando conexao!");
        		
        		Socket socket = serverSocket.accept();//pausa a execucao, espera um cliente se conectar e retorna um socket

        		ServidorThread servidorThread = new ServidorThread(socket);//cria uma thread responsavel por um cliente
        		servidorThread.start();

        		
        		System.out.println("cliente se conectou!");

        		//serverSocket.close(); criar um if para fechar a conexao
    		}		
		}catch(IOException ex) {
			ex.printStackTrace();
			
		}	
	}
}
