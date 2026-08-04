package protocolo;

// classe responsavel por especificar os tipos de mensagens que seria enviadas
public enum TipoMensagem {

    LOGIN,
    CLIENTE,
    PRIVADO,
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
