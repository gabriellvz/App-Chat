package clienteServidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static void main(String[] args) {
        try {
        	ServerSocket serverSocket = new ServerSocket(4000);//inicia um servidor de rede e abre a porta 4000 para se conectar com um cliente
    		
    		System.out.println("servidor aguardando conexão!");
    		
    		Socket socket = serverSocket.accept();//pausa a execucao e espera um cliente se conectar
    		
    		System.out.println("cliente se conectou!");
    		
    		//receber mensagem do cliente
    		
    		//obter fluxo de entrada do socket e converter os bytes para string por meio do InputStreamReader
    		InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());
    		
    		//criar um leitor de buffer para facilitar a leitura
    		BufferedReader reader = new BufferedReader(inputReader);
    		
    		//enviar mensagem pro cliente
    		PrintStream saida = new PrintStream(socket.getOutputStream());
    		
    		String mensagemDoCliente;
    		
    		while((mensagemDoCliente = reader.readLine() ) !=null ) {
    			System.out.println("cliente: " + mensagemDoCliente);
    			
    			saida.println("servidor: " + mensagemDoCliente);		
    			
    		}
    		socket.close();
    		serverSocket.close();
			
		}catch(IOException ex) {
			ex.printStackTrace();
			
		}
		
		

	}

}
