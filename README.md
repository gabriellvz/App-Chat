# 💬 App Chat

Um chat cliente-servidor desenvolvido em **Java** utilizando **Sockets TCP** e **Threads**, permitindo comunicação entre múltiplos clientes em uma mesma rede.

O projeto foi desenvolvido como trabalho da disciplina de **Redes de Computadores 2**, com o objetivo de aplicar conceitos de comunicação cliente-servidor.

---

## 📌 Funcionalidades

- Comunicação utilizando **TCP**.
- Servidor capaz de atender **múltiplos clientes simultaneamente**.
- Cada cliente é atendido por uma **Thread** independente.
- Escolha de endereço IP pelo cliente no momento da conexão.
- Validação de apelido (nickname):
  - Nome vazio.
  - Nome já em uso.
  - Nome iniciado por comandos (`/`).
  - Nome com espaços.
  - Limite máximo de caracteres.
- Listagem de usuários conectados.
- Envio de mensagens públicas.
- Envio de mensagens privadas.
- Notificação de entrada e saída de usuários.
- Interface de terminal com utilização de cores ANSI.
- Tratamento das principais exceções de conexão.

---

## 🛠 Tecnologias utilizadas

- Java 21
- Java Sockets
  
---

## 📁 Estrutura do projeto

```
src
├── cliente
│   ├── Cliente.java
│   └── ClienteThread.java
│
├── servidor
│   ├── Servidor.java
│   └── ServidorThread.java
│
└── protocolo
    ├── TipoMensagem.java
    └── UI.java
```

### Responsabilidade de cada pacote

**cliente**

Responsável pela conexão com o servidor, envio de comandos e recebimento das mensagens.

**servidor**

Gerencia as conexões, mantém a lista de clientes conectados, interpreta os comandos enviados e distribui as mensagens.

**protocolo**

Contém estruturas compartilhadas entre cliente e servidor, como tipos de mensagens e utilitários para interface do terminal.

---

## 📡 Protocolo de aplicação

O projeto utiliza um protocolo de aplicação baseado em **texto simples**.

### Comandos disponíveis

| Comando | Descrição |
|----------|-----------|
| `/listar` | Lista os usuários conectados |
| `/msg_mensagem` | Envia uma mensagem pública |
| `/crs@usuario mensagem` | Envia uma mensagem privada |
| `/sair` | Encerra a conexão |

---

## ▶️ Como executar

### 1. Compile o projeto

```bash
javac src/**/*.java
```

Ou utilize uma IDE como:

- IntelliJ IDEA
- Eclipse
- VS Code

---

### 2. Execute o servidor

```bash
java servidor.Servidor
```

O servidor ficará aguardando conexões na porta **4000**.

---

### 3. Execute um ou mais clientes

```bash
java cliente.Cliente
```

Informe:

- endereço IP do servidor;
- apelido do usuário.

Após a conexão, os comandos do chat estarão disponíveis.

---

## 🔒 Tratamento de erros

O sistema realiza tratamento para diversas situações, como:

- servidor indisponível;
- timeout de conexão;
- endereço IP inválido;
- perda de conexão;
- apelido inválido;
- apelido já existente;
- mensagens vazias;
- mensagens acima do limite permitido;
- tentativa de enviar mensagem privada para si mesmo;
- destinatário inexistente.

---

## 🧵 Concorrência

O servidor utiliza uma arquitetura baseada em **Threads**, onde:

- existe uma Thread principal responsável por aceitar novas conexões;
- cada cliente conectado recebe uma Thread exclusiva (`ServidorThread`);
- as mensagens são distribuídas para os clientes conforme o protocolo implementado.

---

## 🎯 Objetivos de aprendizagem

Este projeto foi desenvolvido para praticar:

- Programação em rede utilizando sockets.
- Comunicação TCP.
- Desenvolvimento de um protocolo de aplicação.
- Comunicação cliente-servidor.

---

## 👨‍💻 Autores

- [@gabriellvz](https://github.com/gabriellvz)
- [@Emerson484](https://github.com/Emerson484) 

---
