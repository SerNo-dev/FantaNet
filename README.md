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
<img src="https://github.com/SerNo-dev/FantaNet/assets/131872447/6d8ce53f-3221-41d9-8aaf-f6736f74bdac" width="800" height="800">

2. **Registrazione**: Component per la registrazione degli utenti.

3. **Login**: Component per il login degli utenti.
4. **Dashboard**: Accessibile dopo la registrazione, con 3000 crediti iniziali.
   - **Mercato**: Visualizzazione delle card dei giocatori con il relativo prezzo.
   - **Carrello**: Navbar con carrello per procedere al pagamento.
   - **Deck**: Creazione della squadra (necessariamente di 7 giocatori).
   - **Gioca**: Scontri contro altri utenti casuali basati sui voti storici dei giocatori.
   - **Replay**: Visualizzazione dei risultati degli scontri dell'utente.

## Istruzioni per l'Installazione

### Clonare la Repository

```sh
git clone <URL-della-repository>

Installazione delle Dipendenze per il Frontend

sh

cd frontend
npm install
ng serve

Installazione delle Dipendenze per il Backend

sh

cd ../backend
mvn install
mvn spring-boot:run

Configurazione del Database

    Importare i dati iniziali in PostgreSQL utilizzando gli script forniti.
    Configurare le credenziali del database nel file application.properties.

Utilizzo della Piattaforma

Una volta avviati entrambi i server, accedere all'applicazione tramite il browser all'indirizzo:

http://localhost:4200

Deploy

La piattaforma è stata anche deployata con l'utilizzo di Koyeb e Netlify e può essere visualizzata
 direttamente a questo link:

https://main--fantanet.netlify.app/

Buon divertimento con la piattaforma di fantacalcio!

Per ulteriori informazioni o assistenza, contattami.

Sergio
