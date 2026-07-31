package clienteServidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static void main(String[] args) {
        try {
        	ServerSocket serverSocket = new ServerSocket(4000);//inicia um servidor de rede e abre a porta 4000 para se conectar com um cliente
    		while(true) {
    			System.out.println("servidor aguardando conexão!");
        		
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
