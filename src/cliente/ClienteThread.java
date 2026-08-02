package cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

//thread para ler a mensagem que o servidor enviou
public class ClienteThread extends Thread{
	private Socket socket;
	private InputStreamReader input;
	private BufferedReader reader;
	//private String nomeUsuario;

	public ClienteThread(Socket socket, BufferedReader reader) {
		this.socket = socket;
		this.reader = reader;
	}

	@Override
	public void run() {
		try {

			//obter o fluxo de entrada pelo socket e transformar os bytes em String por meio do InputStreamReader

			//InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());
			
			//usar um leitor de buffer pra facilitar a leitura

			//BufferedReader reader = new BufferedReader(inputReader);

			//enviar mensagem para o servidor
			PrintStream saida = new PrintStream(socket.getOutputStream());

			String mensagemServidor;

			// ler e imprime a mensagem equanto houver texto
			while((mensagemServidor = reader.readLine()) != null) {
				System.out.println(mensagemServidor);//printar a mensagem que o servidor enviou
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
