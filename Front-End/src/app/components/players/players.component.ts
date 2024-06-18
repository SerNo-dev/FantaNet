import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { AuthData } from 'src/app/interface/auth-data.interface';
import { Giocatore } from 'src/app/interface/giocatore.interface';
import { GiocatoreService } from 'src/app/service/giocatore.service';

@Component({
  selector: 'app-players',
  templateUrl: './players.component.html',
  styleUrls: ['./players.component.scss']
})
export class PlayersComponent implements OnInit {
  giocatori: Giocatore[] = [];
  page: number = 0;
  totalPages: number = 0;
  userId: number | null = null;

  constructor(
    private giocatoreService: GiocatoreService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadGiocatori();
    const user = this.authService.getStoredUser();
    if (user) {
      this.userId = user.id;
    }
  }

  loadGiocatori(): void {
    this.giocatoreService.getGiocatori(this.page, 30).subscribe(
      data => {
        this.giocatori = data.content;
        this.totalPages = data.totalPages;
      },
      error => {
        console.error('Errore nel caricamento dei giocatori', error);
      }
    );
  }

  nextPage(): void {
    if (this.page + 1 < this.totalPages) {
      this.page++;
      this.loadGiocatori();
    }
  }

  previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadGiocatori();
    }
  }

  addToCarrello(giocatore: Giocatore): void {
    const userId = this.authService.getStoredUser()?.id;
    console.log('addToCarrello() -> UserId:', userId);
    if (userId) {
      this.giocatoreService.addGiocatoreToCarrello(userId, giocatore.id).subscribe(
        (response: AuthData) => {
          console.log('Giocatore aggiunto al carrello con successo', response);
          this.authService.updateUser(response); // Aggiorna l'utente solo con la risposta del server
          console.log('addToCarrello() -> User aggiornato:', this.authService.getStoredUser());
        },
        (error: any) => {
          console.error('Errore nell\'aggiunta del giocatore al carrello', error);
        }
      );
    } else {
      console.error('User ID non trovato. L\'utente potrebbe non essere autenticato.');
    }
  }
}