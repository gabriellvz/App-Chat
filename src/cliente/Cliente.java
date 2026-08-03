//package clienteServidor;

package cliente;

//import servidor.Servidor;
//import servidor.ServidorThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import protocolo.TipoMensagem;
import protocolo.UI;

public class Cliente {


	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost",4000); //criar a conexao entre as maquinas
			
			Scanner scanner = new Scanner(System.in); //receber dados do teclado

			// converter a String para bytes e manda eles pro outputStream
			PrintStream saida = new PrintStream(socket.getOutputStream());

			// ler dados do servidor
			//obter fluxo de entrada do socket e converter os bytes para string por meio do InputStreamReader
			InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());

			//criar um leitor de buffer para facilitar a leitura
			BufferedReader reader = new BufferedReader(inputReader);

			//imprimirLinha();
			//System.out.println(menu());
			UI.imprimirMenu();
			while (true){
				System.out.print((TipoMensagem.LOGIN) + "Digite seu nome/apelido: ");
				String nomeUsuario = scanner.nextLine(); // le do teclado

				// envia o nome de usuario para o servidor por meio do socket.getOutputStream
				saida.println(nomeUsuario);

				String resposta = reader.readLine();

				// caso o nome for aceito encerra o loop imediatamente
				if (resposta.equals("NOME_ACEITO")){
					System.out.println(TipoMensagem.LOGIN + "O nome foi aceito.");
					break;
				}
				else{
					System.out.println(TipoMensagem.LOGIN + "O nome ja esta em uso. Digite novamente um nome valido.");
				}

			}

			//thread responsavel por receber mensagens do servidor
			ClienteThread clienteThread = new ClienteThread(socket,reader);
			clienteThread.start();

			// permite continuar lendo varias mensagens
			while(true) {
				//System.out.print("> ");
				String teclado = scanner.nextLine();

				saida.println(teclado);
				if(teclado.equalsIgnoreCase("/sair")) {
					System.out.println("desconectando do chat...");
					break;
				}			
			}
			//fechamento dos recursos
			socket.close();
			scanner.close();
			System.exit(0);
			
		}catch(UnknownHostException hostDesconhecido) {
			hostDesconhecido.printStackTrace();
			
		}catch(IOException entradaSaida) {
			entradaSaida.printStackTrace();

		}
	}
}
