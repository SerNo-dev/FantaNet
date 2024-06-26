import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { AuthData } from 'src/app/interface/auth-data.interface';
import { Giocatore } from 'src/app/interface/giocatore.interface';
import { GiocatoreService } from 'src/app/service/giocatore.service';

@Component({
  selector: 'app-carrello',
  templateUrl: './carrello.component.html',
  styleUrls: ['./carrello.component.scss']
})
export class CarrelloComponent implements OnInit {
  carrello: Giocatore[] = [];
  user: AuthData | null = null;
  totaleSpesa: number = 0;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(private giocatoreService: GiocatoreService, private authService: AuthService) {}

  ngOnInit(): void {
    const storedUser = this.authService.getStoredUser();
    if (storedUser) {
      this.authService.getUserById(storedUser.id).subscribe(user => {
        this.user = user;
        this.carrello = user.carrello;
        this.calcolaTotaleSpesa();
      });
    }
    this.authService.user$.subscribe(updatedUser => {
      if (updatedUser) {
        this.user = updatedUser;
        this.carrello = updatedUser.carrello;
        this.calcolaTotaleSpesa();
      }
    });
  }

  calcolaTotaleSpesa(): void {
    this.totaleSpesa = this.carrello.reduce((acc, giocatore) => acc + giocatore.prezzo, 0);
  }

  confermaAcquisto(): void {
    this.successMessage = null;
    this.errorMessage = null;

    const userId = this.user?.id;
    const giocatoriIds = this.carrello.map(giocatore => giocatore.id);

    if (userId && giocatoriIds.length > 0) {
      if ((this.user?.crediti ?? 0) >= this.totaleSpesa) {
        this.giocatoreService.addGiocatoriToUser(userId, giocatoriIds).subscribe(
          updatedUser => {
            console.log('Purchase confirmed successfully', updatedUser);
            this.authService.updateUser(updatedUser);

            // Clear the cart locally and update the user
            if (this.user) {
              this.user.carrello = [];
              this.authService.updateUser(this.user);
              this.carrello = [];
            }
            this.successMessage = 'Acquisto confermato con successo!';
          },
          error => {
            console.error('Error confirming purchase', error);
          }
        );
      } else {
        this.errorMessage = 'Crediti insufficienti per completare l\'acquisto.';
      }
    } else {
      this.errorMessage = 'Token non trovato o lista degli ID dei giocatori vuota';
    }
  }

  removeFromCarrello(giocatoreId: number): void {
    if (this.user) {
      this.giocatoreService.removeFromCarrello(this.user.id, giocatoreId).subscribe(
        response => {
          console.log('Player successfully removed from cart', response);
          this.authService.updateUser(response);
          this.carrello = response.carrello;
          this.calcolaTotaleSpesa();
        },
        error => {
          console.error('Error removing player from cart', error);
        }
      );
    }
  }
}
