import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { AuthData } from 'src/app/interface/auth-data.interface';
import { GiocatoriVotiNellePartite } from 'src/app/interface/giocatori-voti-nelle-partite.interface';

import { GiocatoriVotiService } from 'src/app/service/giocatorivoti.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent  {
  user: AuthData | null = null;
  opponent: AuthData | null = null;
  winner: AuthData | null = null;
  errorMessage: string | null = null;

  userDeck: GiocatoriVotiNellePartite[] = [];
  opponentDeck: GiocatoriVotiNellePartite[] = [];

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private votiService: GiocatoriVotiService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getStoredUser();
    if (this.user) {
      this.loadUserDeck(this.user.id);
    }
  }

  loadUserDeck(userId: number): void {
    this.votiService.getVotiByUserDeck(userId).subscribe(
      (deck) => {
        this.userDeck = deck;
      },
      (error) => {
        console.error('Error getting votes for user deck:', error);
        this.errorMessage = 'Error getting votes for user deck';
      }
    );
  }

  findRandomOpponent(): void {
    this.userService.getRandomUser().subscribe(
      (user) => {
        this.opponent = user;
        if (this.opponent) {
          this.loadOpponentDeck(this.opponent.id);
        }
      },
      (error) => {
        console.error('Error finding random opponent:', error);
        this.errorMessage = 'Error finding random opponent';
      }
    );
  }

  loadOpponentDeck(userId: number): void {
    this.votiService.getVotiByUserDeck(userId).subscribe(
      (deck) => {
        this.opponentDeck = deck;
        if (this.user && this.opponent) {
          this.battle(this.user, this.opponent);
        }
      },
      (error) => {
        console.error('Error getting votes for opponent deck:', error);
        this.errorMessage = 'Error getting votes for opponent deck';
      }
    );
  }

  battle(user1: AuthData, user2: AuthData): void {
    const user1Score = this.calculateTotalScore(this.userDeck);
    const user2Score = this.calculateTotalScore(this.opponentDeck);

    if (user1Score > user2Score) {
      this.winner = user1;
    } else {
      this.winner = user2;
    }
  }

  calculateTotalScore(votes: GiocatoriVotiNellePartite[]): number {
    return votes.reduce((total, vote) => {
      const rating = vote.rating !== null ? vote.rating : 0;
      return total + rating;
    }, 0);
  }
}