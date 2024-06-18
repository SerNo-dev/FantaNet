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

  constructor(private giocatoreService: GiocatoreService, private authService: AuthService) {}

  ngOnInit(): void {
    const storedUser = this.authService.getStoredUser();
    if (storedUser) {
      this.authService.getUserById(storedUser.id).subscribe(user => {
        this.user = user;
        this.carrello = user.carrello;
      });
    }
    this.authService.user$.subscribe(updatedUser => {
      if (updatedUser) {
        this.user = updatedUser;
        this.carrello = updatedUser.carrello;
      }
    });
  }

  confermaAcquisto(): void {
    const userId = this.user?.id;
    const giocatoriIds = this.carrello.map(giocatore => giocatore.id);

    if (userId && giocatoriIds.length > 0) {
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
        },
        error => {
          console.error('Error confirming purchase', error);
        }
      );
    } else {
      console.error('Token not found or player ID list is empty');
    }
  }

  removeFromCarrello(giocatoreId: number): void {
    if (this.user) {
      this.giocatoreService.removeFromCarrello(this.user.id, giocatoreId).subscribe(
        response => {
          console.log('Player successfully removed from cart', response);
          this.authService.updateUser(response);
          this.carrello = response.carrello;
        },
        error => {
          console.error('Error removing player from cart', error);
        }
      );
    }
  }
}
