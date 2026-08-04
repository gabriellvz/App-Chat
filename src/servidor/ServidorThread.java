//package clienteServidor;

package servidor;

//import cliente.ClienteThread;

//import protocolo.TipoMensagem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import protocolo.TipoMensagem;

//thread responsavel por conversar com apenas 1 cliente deixando a thread principal (main) livre
public class ServidorThread extends Thread{
	private Socket socket;
	private String nomeUsuario;
	private boolean conectado = true;
	private PrintStream saida;

	private BufferedReader reader;

	public ServidorThread(Socket socket){
		this.socket = socket;

	}

	public String getNomeUsuario (){
		return this.nomeUsuario;
	}

	public boolean getConectado(boolean conectado) {
		return this.conectado;
	}
	//metodo usado quando o cliente pedir para sair do chat
	public void desconectar() {
		this.conectado=false;
	}

	// metodo responsavel por enviar uma mensagem pelo sokcet
	public void enviarMensagem (String mensagem){
		this.saida.println(mensagem);
	}

	public void validarNome (BufferedReader reader) throws IOException {
		// loop responsavel por verificar um nome ate que ele seja valido
		while (true){
			this.nomeUsuario = reader.readLine();

			// se o nome de usuario for nulo, o socket sera fechado
			if (this.nomeUsuario == null){
				socket.close();
				return; // encerra o run
			}

			if (Servidor.nomeJaExiste(this.nomeUsuario)){
				saida.println("NOME_EM_USO"); // msg para o socket
			}
			else {
				//adiciona o objeto atual a lista de servidorThread
				Servidor.adicionar(this);
				System.out.println(Servidor.listarUsuariosConectados()); // retorna usuarios no servidor sempre que um novo se conectar
				saida.println("NOME_ACEITO"); // msg para o socket
				Servidor.broadcast(TipoMensagem.INFO + this.nomeUsuario + " entrou no chat.");
				break;
			}
		}

	}

	
    @Override
    public void run() {
    	try {
    		//receber mensagem do cliente
    		
    		//obter fluxo de entrada do socket e converter os bytes para string por meio do InputStreamReader
    		InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());
    		
    		//criar um leitor de buffer para facilitar a leitura
    		reader = new BufferedReader(inputReader);
    		
    		//enviar mensagem pro cliente
    		saida = new PrintStream(socket.getOutputStream());
    		
			validarNome(reader);

    		String mensagemDoCliente;


    		// imprime a mensagem do cliente enquanto ele estiver conectado e tiver texto para ler
    		while(conectado && (mensagemDoCliente = reader.readLine() ) !=null) {

    			System.out.println(TipoMensagem.CLIENTE + nomeUsuario + ": " + mensagemDoCliente );
    			Servidor.verificarComando(this, mensagemDoCliente);
				
				if(!conectado) {//se o cliente quiser sair do chat ele sai do while e vai pro finally
					break;
				}
				//Servidor.broadcast(nomeUsuario + ": " + mensagemDoCliente);
    			
    			//saida.println("servidor: " + mensagemDoCliente); essa linha deixa de ser necessaria pois o broadcast ja envia  a mensagem
    		}
    		socket.close();
    		
    	} catch(IOException ex) {
    		System.out.println(TipoMensagem.SERVIDOR + "Conexao perdida com o cliente: " + nomeUsuario);

    	} finally {

    		if(nomeUsuario !=null) {
    			Servidor.remover(this);//remove o cliente
    			Servidor.broadcast(TipoMensagem.INFO + nomeUsuario + " saiu do chat!");//avisa a todos os clientes que aquele cliente saiu
    			System.out.println(TipoMensagem.SERVIDOR + nomeUsuario + " foi desconectado do servidor"); //mostrar no servidor que o cliente foi desconectado
    		}
    		//fechar o socket de forma segura
    		try {
    			if(socket!=null) {
    				socket.close();
    			}
    			
    		}catch(IOException ex) {
    			ex.printStackTrace();
    		}
    	}
    	
    }
}
