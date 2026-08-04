//package clienteServidor;

package cliente;

//import servidor.Servidor;
//import servidor.ServidorThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import protocolo.TipoMensagem;
import protocolo.UI;

public class Cliente {

	private static Socket conectar (Scanner scanner){
		Socket socket = null;
		while(true){
			System.out.print(TipoMensagem.INFO + "Digite o numero IP para se conectar ao servidor: ");
			String numeroIP = scanner.nextLine().trim(); // trim remove espacos

			// essas excessoes serao tratadas no momento em que o cliente tentar se conectar ao servidor
			try{
				socket = new Socket(numeroIP, 4000);
				System.out.println(TipoMensagem.INFO + "Conexao estabelecida");

				return socket;
			}catch (UnknownHostException hostException){ // excessao para caso um host nao for encotrado (ip invalido)
				System.out.println(TipoMensagem.ERRO + "Host nao encontrado.");
			}catch (IOException ioException){
				System.out.println(TipoMensagem.ERRO + "Servidor indisponivel..."); // algum outro problema na conexao
			}

		}
	}

	public static void main(String[] args) {

		Socket socket;

		try {

			UI.imprimirMenu();
			//System.out.println(endereco);
			Scanner scanner = new Scanner(System.in); //receber dados do teclado

			socket = conectar(scanner);

            // converter a String para bytes e manda eles pro outputStream
			PrintStream saida = new PrintStream(socket.getOutputStream());

			// ler dados do servidor
			//obter fluxo de entrada do socket e converter os bytes para string por meio do InputStreamReader
			InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());

			//criar um leitor de buffer para facilitar a leitura
			BufferedReader reader = new BufferedReader(inputReader);



			//imprimirLinha();
			//System.out.println(menu());
			while (true){
				System.out.print((TipoMensagem.LOGIN) + "Digite seu nome/apelido: ");
				String nomeUsuario = scanner.nextLine(); // le do teclado

				// envia o nome de usuario para o servidor por meio do socket.getOutputStream
				saida.println(nomeUsuario);

				String resposta = reader.readLine(); // le mensagem do socket

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
