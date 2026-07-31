package clienteServidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

//thread para ler o que o servidor enviou de volta
public class ClienteThread extends Thread{
	private Socket socket;
	
	public ClienteThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			
			//obter o fluxo de entrada pelo socket e transoformar os bytes por meio do InputStreamReader
			InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());
			
			//usar um leitor de buffer pra facilitar a leitura
			BufferedReader reader = new BufferedReader(inputReader);
			
			String mensagem;
			
			// ler e imprime a mensagem equanto houver texto
			while((mensagem = reader.readLine()) != null) {
				System.out.println(mensagem);//printar a mensagem que o servidor enviou
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
