package protocolo;

// classe responsavel por especificar os tipos de mensagens que seria enviadas
public enum TipoMensagem {
    
    LOGIN,
    CLIENTE,
    PRIVADO, // utilizar quando for implementar o chat privado
    GERAL,
    LISTA,
    SERVIDOR,
    ERRO,
    INFO;


    @Override
    public String toString(){
        return "[" + name() + "] "; // name() retorna o valor do tipo
    }

}
