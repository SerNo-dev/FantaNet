import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { AuthData } from 'src/app/interface/auth-data.interface';
import { Giocatore } from 'src/app/interface/giocatore.interface';
import { GiocatoreService } from 'src/app/service/giocatore.service';

@Component({
  selector: 'app-deck',
  templateUrl: './deck.component.html',
  styleUrls: ['./deck.component.scss']
})
export class DeckComponent implements OnInit {
  giocatoriAcquistati: Giocatore[] = [];
  deck: Giocatore[] = [];
  user: AuthData | null = null;
  deckError: string | null = null;

  constructor(private authService: AuthService, private giocatoreService: GiocatoreService) {}

  ngOnInit(): void {
    const storedUser = this.authService.getStoredUser();
    if (storedUser) {
      this.authService.getUserById(storedUser.id).subscribe(user => {
        this.user = user;
        this.giocatoriAcquistati = user.giocatoriAcquistati;
        this.deck = user.deck || [];
        console.log('User loaded:', user);
        console.log('Initial deck:', this.deck);
      });
    }
    this.authService.user$.subscribe(updatedUser => {
      if (updatedUser) {
        this.user = updatedUser;
        this.giocatoriAcquistati = updatedUser.giocatoriAcquistati;
        this.deck = updatedUser.deck || [];
        console.log('User updated:', updatedUser);
        console.log('Updated deck:', this.deck);
      }
    });
  }

  addToDeck(giocatore: Giocatore): void {
    if (this.isInDeck(giocatore)) {
      this.deckError = 'Player is already in the deck.';
      console.log('Player is already in the deck:', giocatore);
      return;
    }

    const { valid, message } = this.verificaDeck([...this.deck, giocatore]);
    if (!valid) {
      this.deckError = message;
      console.log('Deck verification failed:', message);
      return;
    }

    if (this.user) {
      this.giocatoreService.addToDeck(this.user.id, giocatore.id).subscribe(
        updatedUser => {
          this.authService.updateUser(updatedUser);
          // Aggiorna il deck con i dati dell'utente aggiornato
          this.deck = updatedUser.deck || [];
          console.log('Player added to deck:', giocatore);
          console.log('Current deck:', this.deck);
          this.deckError = null;
        },
        error => {
          console.error('Error adding player to deck', error);
        }
      );
    }
  }

  removeFromDeck(giocatoreId: number): void {
    if (!this.isInDeckById(giocatoreId)) {
      this.deckError = 'Player is not in the deck.';
      console.log('Player is not in the deck:', giocatoreId);
      return;
    }

    if (this.user) {
      this.giocatoreService.removeFromDeck(this.user.id, giocatoreId).subscribe(
        updatedUser => {
          this.authService.updateUser(updatedUser);
          // Aggiorna il deck con i dati dell'utente aggiornato
          this.deck = updatedUser.deck || [];
          console.log('Player removed from deck:', giocatoreId);
          console.log('Current deck:', this.deck);
        },
        error => {
          console.error('Error removing player from deck', error);
        }
      );
    }
  }

  isInDeck(giocatore: Giocatore): boolean {
    return this.deck.some(deckPlayer => deckPlayer.id === giocatore.id);
  }

  isInDeckById(giocatoreId: number): boolean {
    return this.deck.some(deckPlayer => deckPlayer.id === giocatoreId);
  }

  verificaDeck(deck: Giocatore[]): { valid: boolean, message: string } {
    let goalkeepers = 0, defenders = 0, midfielders = 0, attackers = 0;
    deck.forEach(giocatore => {
      switch (giocatore.posizione) {
        case 'Goalkeeper':
          goalkeepers++;
          break;
        case 'Defender':
          defenders++;
          break;
        case 'Midfielder':
          midfielders++;
          break;
        case 'Attacker':
          attackers++;
          break;
      }
    });

    if (deck.length > 7) return { valid: false, message: 'You can have only 7 players in your deck.' };
    if (goalkeepers > 1) return { valid: false, message: 'You can have only 1 goalkeeper.' };
    if (defenders > 2) return { valid: false, message: 'You can have only 2 defenders.' };
    if (midfielders > 2) return { valid: false, message: 'You can have only 2 midfielders.' };
    if (attackers > 2) return { valid: false, message: 'You can have only 2 attackers.' };

    if (deck.length === 7) {
      if (goalkeepers < 1) return { valid: false, message: 'You must have at least 1 goalkeeper.' };
      if (defenders < 2) return { valid: false, message: 'You must have at least 2 defenders.' };
      if (midfielders < 2) return { valid: false, message: 'You must have at least 2 midfielders.' };
      if (attackers < 2) return { valid: false, message: 'You must have at least 2 attackers.' };
    }

    return { valid: true, message: 'Deck is valid' };
  }
}
