# Piattaforma di Fantacalcio Online

## Descrizione del Progetto

Questo progetto è una piattaforma di fantacalcio online innovativa che permette agli utenti di registrarsi, creare squadre utilizzando crediti virtuali, sfidare altri utenti in scontri casuali e visualizzare i risultati basati sulle prestazioni storiche dei giocatori.

## Tecnologie Utilizzate

- **Frontend**
  - Angular
  - HTML
  - CSS
  - Bootstrap
  - SASS

- **Backend**
  - Java con Spring Boot
  - WebSocket

- **Database**
  - PostgreSQL

- **Autenticazione**
  - JSON Web Tokens (JWT)
  - bcrypt

- **API**
  - API-FOOTBALL

## Funzionalità Principali

1. **Home**: Pagina principale accattivante con una hero.
<img width="1438" alt="homehero" src="https://github.com/SerNo-dev/FantaNet/assets/131872447/29ac321d-a1d9-4775-af71-9a4c62705300">

2. **Registrazione**: Component per la registrazione degli utenti.

3. **Login**: Component per il login degli utenti.
4. **Dashboard**: Accessibile dopo la registrazione, con 3000 crediti iniziali.
   - **Mercato**: Visualizzazione delle card dei giocatori con il relativo prezzo.
   - **Carrello**: Navbar con carrello per procedere al pagamento.
   - **Deck**: Creazione della squadra (necessariamente di 7 giocatori).
   - **Gioca**: Scontri contro altri utenti casuali basati sui voti storici dei giocatori.
   - **Replay**: Visualizzazione dei risultati degli scontri dell'utente.

## Istruzioni per l'Installazione

### 1.Clonare la Repository

git clone <URL-della-repository>

### 2.Installazione delle Dipendenze per il Frontend


cd frontend
npm install
ng serve

### 3.Installazione delle Dipendenze per il Backend

cd ../backend
mvn install
mvn spring-boot:run

### 4.Configurazione del Database

 Importare i dati iniziali in PostgreSQL utilizzando gli script forniti.
 Configurare le credenziali del database nel file application.properties.

Utilizzo della Piattaforma

Una volta avviati entrambi i server, accedere all'applicazione tramite il browser all'indirizzo:

http://localhost:4200

Deploy

La piattaforma è stata anche deployata con l'utilizzo di Koyeb e Netlify e può essere visualizzata
 direttamente a questo link:

[FantaNet](https://fantanet.netlify.app/)

## Diritti d'Autore

Tutti i contenuti di questo sito, inclusi il codice sorgente, la grafica, il design e i dati, sono di proprietà esclusiva di FantaNet e sono protetti dalle leggi sul diritto d'autore e sulla proprietà intellettuale. È vietata la riproduzione, distribuzione, modifica, o utilizzo del contenuto per scopi commerciali senza l'esplicito consenso scritto dell'autore.

Buon divertimento con la piattaforma di fantacalcio!

Per ulteriori informazioni o assistenza, contattami.


Sergio
