package cliente;

import servidor.Servidor;
import servidor.ServidorThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {

	public static String menu(){
		return  "Bem vindo ao zap zap 2.0\n\n" +
				"Comandos  disponiveis:\n\n" +
				"/listar - Retorna uma lista com todos os usuarios conectados no chat.\n" +
				"/msg - Envia uma mensagem\n" +
				"/criar_sala - Cria uma sala de chat privado.\n" +
				"/sair - O usuario sera desconectado do chat.\n\n" +
				"Instrucoes adicionais:\n" +
				"1. Para utilizar o comando /msg eh necessario utilizar o formato: /msg_mensagem\n" +
				"2. Para utilizar o comando /criar_sala eh necessario utilizar o formato: /criar_sala@nomeUsuario\n";

	}

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost",4000); //criar a conexao entre as maquinas
			
			Scanner scanner = new Scanner(System.in); //receber dados do teclado


			//ClienteThread clienteThread = new ClienteThread(socket);
			//clienteThread.start();

			// converter a String para bytes e manda eles pro outputStream
			PrintStream saida = new PrintStream(socket.getOutputStream());

			// ler dados do servidor
			//obter fluxo de entrada do socket e converter os bytes para string por meio do InputStreamReader
			InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());

			//criar um leitor de buffer para facilitar a leitura
			BufferedReader reader = new BufferedReader(inputReader);

			System.out.println(menu());
			while (true){
				System.out.print("Digite seu nome/apelido: ");
				String nomeUsuario = scanner.nextLine(); // le do teclado

				// envia o nome de usuario para o servidor por meio do socket.getOutputStream
				saida.println(nomeUsuario);

				String resposta = reader.readLine();

				// caso o nome for aceito encerra o loop imediatamente
				if (resposta.equals("NOME_ACEITO")){
					System.out.println("O nome foi aceito.");
					//nomeUsuario = scanner.nextLine(); // le do teclado
					break;
				}
				else{
					System.out.println("O nome ja esta em uso. Digite novamente um nome valido.");
				}

			}

			//thread responsavel por receber mensagens do servidor
			ClienteThread clienteThread = new ClienteThread(socket,reader);
			clienteThread.start();

			// permite continuar lendo varias mensagens
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
