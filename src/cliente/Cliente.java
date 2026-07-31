package clienteServidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost",4000); //criar a conexao entre as maquinas
			
			Scanner scanner = new Scanner(System.in); //receber dados do teclado
			
			ClienteThread clienteThread = new ClienteThread(socket);
			clienteThread.start();

			// converter a String para bytes e manda eles pro outputStream
			PrintStream saida = new PrintStream(socket.getOutputStream());
			
			while(true) {
				String teclado = scanner.nextLine();
				saida.println(teclado);
				//scanner.close();
				
			}
			
		}catch(UnknownHostException hostDesconhecido) {
			hostDesconhecido.printStackTrace();
			
		}catch(IOException entradaSaida) {
			entradaSaida.printStackTrace();
		}	

	} 
	

}
