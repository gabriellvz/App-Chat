package clienteServidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

//thread responsavel por conversar com apenas 1 cliente deixando a thread principal (main) livre
public class ServidorThread extends Thread{
	Socket socket;
	
	public ServidorThread(Socket socket){
		this.socket = socket;
		
	}
	
    @Override
    public void run() {
    	try {
    		//receber mensagem do cliente
    		
    		//obter fluxo de entrada do socket e converter os bytes para string por meio do InputStreamReader
    		InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());
    		
    		//criar um leitor de buffer para facilitar a leitura
    		BufferedReader reader = new BufferedReader(inputReader);
    		
    		//enviar mensagem pro cliente
    		PrintStream saida = new PrintStream(socket.getOutputStream());
    		
    		String mensagemDoCliente;
    		
    		// ler e imprime a mensagem equanto houver texto
    		while((mensagemDoCliente = reader.readLine()) !=null ) {
    			System.out.println("cliente: " + mensagemDoCliente);
    			
    			saida.println("servidor: " + mensagemDoCliente);		
    			
    		}
    		socket.close();
    		
    	}catch(IOException ex) {
    		ex.printStackTrace();
    	}
    	
    }
}
