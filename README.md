# BkPi-Telegram-bot

---

### _Italiano_

BkPi-Telegram-bot è un bot Telegram scritto in Java 11, gestito tramite Maven e Docker, progettato per automatizzare attività su Raspberry Pi.

#### Caratteristiche principali

- Controllo remoto e notifiche via Telegram
- Deployment semplice tramite container Docker su Raspberry Pi
- Estendibile con comandi personalizzati

#### Tecnologie utilizzate

- **Java 11**
- **Maven**
- **Docker**

#### Comandi disponibili e spiegazione

- `IP`  
  Restituisce l'indirizzo IP del Raspberry Pi. Utile per verificare la connettività o conoscere l'indirizzo della macchina su cui gira il bot.

- `PING`  
  Risponde con "PONG". Serve per verificare che il bot sia attivo e raggiungibile.

- Invio di foto, video e documenti  
  Ogni file inviato tramite Telegram viene salvato in un percorso configurato nel file di properties del bot (path interno).  
  Tramite l'opzione Docker `-v <<PERCORSO_LOCALE>>:/app/BotMessages`, puoi montare una cartella locale del tuo Raspberry Pi, così i file ricevuti dal bot saranno accessibili anche fuori dal container.

Se viene inoltrato un comando non previsto, il bot risponde con il messaggio di errore:  
`Unknown action`

#### Prerequisiti

- Java 11
- Docker installato su Raspberry Pi
- Account Telegram e token del bot

#### Installazione

1. Clona il repository:
   ```bash
   git clone https://github.com/Daniel91z/BkPi-Telegram-bot.git
   ```

2. Costruisci l’immagine Docker:
   ```bash
   docker build -t telegrambot .
   ```

3. Avvia il container Docker, montando la cartella locale dove salvare foto, video e documenti:
   ```bash
   docker run -d -v <<PERCORSO_LOCALE>>:/app/BotMessages telegrambot
   ```

#### Utilizzo

Interagisci con il bot da Telegram utilizzando i comandi disponibili o inviando file (foto, video, documenti).

#### Autore

- [Daniel91z](https://github.com/Daniel91z)

---

### _English_

BkPi-Telegram-bot is a Telegram bot written in Java 11, managed with Maven and Docker, designed to automate tasks on Raspberry Pi.

#### Main features

- Remote control and notifications via Telegram
- Simple deployment using a Docker container on Raspberry Pi
- Extensible with custom commands

#### Technologies used

- **Java 11**
- **Maven**
- **Docker**

#### Available commands and explanation

- `IP`  
  Returns the Raspberry Pi's IP address. Useful for checking connectivity or knowing the address of the machine where the bot runs.

- `PING`  
  Replies with "PONG". Used to check that the bot is active and reachable.

- Sending photos, videos, and documents  
  Any file sent via Telegram is saved to a path configured in the bot's properties file (internal path).  
  Using the Docker option `-v <<LOCAL_PATH>>:/app/BotMessages`, you can mount a local folder from your Raspberry Pi, so files received by the bot are accessible even outside the container.

If an unrecognized command is sent, the bot replies with the error message:  
`Unknown action`

#### Prerequisites

- Java 11
- Docker installed on Raspberry Pi
- Telegram account and bot token

#### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Daniel91z/BkPi-Telegram-bot.git
   ```

2. Build the Docker image:
   ```bash
   docker build -t telegrambot .
   ```

3. Start the Docker container, mounting your local folder for saving photos, videos, and documents:
   ```bash
   docker run -d -v <<LOCAL_PATH>>:/app/BotMessages telegrambot
   ```

#### Usage

Interact with the bot from Telegram using the available commands or by sending files (photos, videos, documents).

#### Author

- [Daniel91z](https://github.com/Daniel91z)