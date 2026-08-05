package cliente;

import protocolo.TipoMensagem;
import protocolo.UI;

import java.io.BufferedReader;
import java.net.Socket;

//thread para ler a mensagem que o servidor enviou
public class ClienteThread extends Thread{
	private Socket socket;
	private final BufferedReader reader;

	public ClienteThread(Socket socket, BufferedReader reader) {
		this.socket = socket;
		this.reader = reader;
	}

	@Override
	public void run() {
		try {
			String mensagemServidor;
			// ler e imprime a mensagem equanto houver texto
			while((mensagemServidor = reader.readLine()) != null) {
				System.out.println(mensagemServidor);//printar a mensagem que o servidor enviou
			}
		} catch(Exception ex) {
			//excecao para o caso de o socket ser fechado intencionalmente,nao faz nada,apenas encerra a thread de forma silenciosa
			if(ex.getMessage() != null && ex.getMessage().contains("Socket closed") || ex.getMessage().contains("Connection reset")) {			
				System.out.println(TipoMensagem.INFO + UI.estilizarMensagem('Y',"Conexao perdida com o servidor "));
				System.exit(0);
			}else {
				ex.printStackTrace();
			}
		}
	}
}
