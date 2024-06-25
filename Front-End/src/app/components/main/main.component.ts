import { Component } from '@angular/core';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent {
  players = [
    {
      imageUrl: 'assets/imgs/card1n.jpg',
      description: `
      Immagina di poter creare la tua squadra dei sogni ogni settimana, 
      utilizzando crediti virtuali per selezionare i migliori giocatori disponibili.
       Ogni utente ha la possibilità di iscriversi gratuitamente e ricevere un pacchetto iniziale di crediti 
      per iniziare la propria avventura nel mondo del fantacalcio futuristico.
      `,title: 'Crea la tua Squadra Ideale'
    },
    {
      imageUrl: 'assets/imgs/card2.jpg',
      description: `
        Utilizza i tuoi crediti per creare una squadra competitiva ogni settimana. 
        Seleziona i giocatori in base alle loro prestazioni reali e strategizza per battere i tuoi avversari. 
        Ricorda, ogni settimana avrai la possibilità di creare una nuova rosa per mantenere la tua squadra 
        sempre al top.
      `,title: 'Crea la tua Squadra dei Sogni'
    },
    {
      imageUrl: 'assets/imgs/card3.jpg',
      description: `
     Una volta formata la tua squadra, è il momento di metterla alla prova.
     Sfida un utente casuale del sito e competi per guadagnare crediti e prestigio.
     Il risultato della partita determinerà se guadagni o perdi crediti e 
     come ti posizioni nelle classifiche globali.
      `, title: 'Sfida gli Avversari' 
    },
  ];
}
