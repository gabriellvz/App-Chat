package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import protocolo.TipoMensagem;
import protocolo.UI;

public class Cliente {

	private static Socket conectar (Scanner scanner){
		Socket socket;
		while(true){
			System.out.print(TipoMensagem.INFO + UI.estilizarMensagem('G',"Digite o numero IP para se conectar ao servidor: "));
			String numeroIP = scanner.nextLine().trim(); // trim remove espacos

			// essas excessoes serao tratadas no momento em que o cliente tentar se conectar ao servidor
			try{
				socket = new Socket(numeroIP, 4000);
				System.out.println(TipoMensagem.INFO + UI.estilizarMensagem('G', "Conexao estabelecida"));

				return socket;
			}catch (UnknownHostException hostException){ // excessao para caso um host nao for encotrado (ip invalido)
				System.out.println(TipoMensagem.ERRO + UI.estilizarMensagem('R',"Host nao encontrado."));
			}catch (IOException ioException){
				System.out.println(TipoMensagem.ERRO + UI.estilizarMensagem('R', "Servidor indisponivel...")); // algum outro problema na conexao
			}

		}
	}

	// metodo para receber a validacao do nome que foi feita pelo servidor
	public static void receberRespostaDoServidor(Scanner scanner, PrintStream saida, BufferedReader reader) throws IOException {

		parar: // rotulo para indicar qual break deve encerrar o loop
		while (true){
			System.out.print((TipoMensagem.LOGIN) + UI.estilizarMensagem('G',"Digite seu nome/apelido: "));
			String nomeUsuario = scanner.nextLine(); // le do teclado

			// envia o nome de usuario para o servidor por meio do socket.getOutputStream
			saida.println(nomeUsuario);

			String resposta = reader.readLine(); // le mensagem do socket

			switch (resposta) {
				case "NOME_VAZIO":
					System.out.println(TipoMensagem.ERRO + UI.estilizarMensagem('R', "Nao eh permitido digitar nome vazio."));
					break;

				case "FORMATO_INCORRETO":
					System.out.println(TipoMensagem.ERRO + UI.estilizarMensagem('R', "Nao eh permitido utilizar formato de comandos para o nome."));
					break;

				// caso o nome for aceito encerra o loop imediatamente
				case "NOME_ACEITO":
					System.out.println(TipoMensagem.LOGIN + UI.estilizarMensagem('G', "O nome foi aceito."));
					System.out.println("\n" + TipoMensagem.INFO + UI.estilizarMensagem('Y', "[CONECTADO COMO " + nomeUsuario.toUpperCase() + "]\n"));
					break parar;

				// se o nome estiver em uso
				case "NOME_EM_USO":
					System.out.println(TipoMensagem.ERRO + UI.estilizarMensagem('R', "O nome ja esta em uso. Digite novamente um nome valido."));
					break;

				case "NOME_LONGO":
					System.out.println(TipoMensagem.ERRO + UI.estilizarMensagem('R', "O maximo de caracteres eh 20."));
					break;

				case "NOME_COM_ESPACO":
					System.out.println(TipoMensagem.ERRO + UI.estilizarMensagem('R', "Nomes com espaco nao sao permitidos."));
					break;
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

			// envia o nome de usuario para ser validados e le a resposta do servidor
			receberRespostaDoServidor(scanner, saida, reader);

            //thread responsavel por receber mensagens do servidor
			ClienteThread clienteThread = new ClienteThread(socket,reader);
			clienteThread.start();

			// permite continuar lendo varias mensagens
			while(true) {
				//System.out.print("> ");
				String teclado = scanner.nextLine();

				saida.println(teclado);
				if(teclado.equalsIgnoreCase("/sair")) {
					System.out.println(UI.estilizarMensagem('G',"desconectando do chat..."));
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
