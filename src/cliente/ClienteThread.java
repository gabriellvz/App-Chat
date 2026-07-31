package clienteServidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

//thread para ler a mensagem que o servidor enviou
public class ClienteThread extends Thread{
	private Socket socket;
	
	public ClienteThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			
			//obter o fluxo de entrada pelo socket e transformar os bytes em String por meio do InputStreamReader
			InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());
			
			//usar um leitor de buffer pra facilitar a leitura
			BufferedReader reader = new BufferedReader(inputReader);
			
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
