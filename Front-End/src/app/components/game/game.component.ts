import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { AuthData } from 'src/app/interface/auth-data.interface';
import { GiocatoriVotiNellePartite } from 'src/app/interface/giocatori-voti-nelle-partite.interface';
import { GiocatoriVotiService } from 'src/app/service/giocatorivoti.service';
import { UserService } from 'src/app/service/user.service';
import { WebSocketService } from 'src/app/service/web-socket.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit, OnDestroy {
  user: AuthData | null = null;
  opponent: AuthData | null = null;
  winner: AuthData | null = null;
  errorMessage: string | null = null;
  userDeck: GiocatoriVotiNellePartite[] = [];
  opponentDeck: GiocatoriVotiNellePartite[] = [];

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private votiService: GiocatoriVotiService,
    private webSocketService: WebSocketService,
    private http: HttpClient, // Aggiungi HttpClient
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getStoredUser();
    if (this.user) {
      this.loadUserDeck(this.user.id);
    }

    this.webSocketService.connect();
    this.webSocketService.messages.subscribe(message => {
      this.handleWebSocketMessage(message);
    });
  }

  ngOnDestroy(): void {
    this.webSocketService.disconnect();
  }

  handleWebSocketMessage(message: any): void {
    console.log('Received message: ', message);
  
    if (message.userDeck && message.opponentDeck && message.winner) {
      if (message.userId === this.user?.id) {
        this.userDeck = message.userDeck;
        this.opponentDeck = message.opponentDeck;
        this.opponent = {
          ...message.opponent,
          avatar: message.opponent.avatar || 'default-avatar.png'
        };
      } else {
        this.opponentDeck = message.userDeck;
        this.userDeck = message.opponentDeck;
        this.opponent = {
          ...message.user,
          avatar: message.user.avatar || 'default-avatar.png'
        };
      }
      this.winner = message.winner;
      this.cdr.detectChanges();
    }
  }
  

  loadUserDeck(userId: number): void {
    this.votiService.getVotiByUserDeck(userId).subscribe(
      (deck) => {
        this.userDeck = deck;
        this.cdr.detectChanges();
      },
      (error) => {
        console.error('Error getting votes for user deck:', error);
        this.errorMessage = 'Error getting votes for user deck';
      }
    );
  }

  findRandomOpponent(): void {
    if (this.user) {
      this.userService.getRandomUser(this.user.id).subscribe(
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
  }

  loadOpponentDeck(userId: number): void {
    console.log('Loading opponent deck for user ID:', userId); // Log per tracciare la chiamata
    this.votiService.getVotiByUserDeck(userId).subscribe(
      (deck) => {
        this.opponentDeck = deck;
        if (this.user && this.opponent) {
          this.battle(this.user, this.opponent);
  
          const userWithAvatar = {
            ...this.user,
            avatar: this.user.avatar || 'default-avatar.png'
          };
  
          const opponentWithAvatar = {
            ...this.opponent,
            avatar: this.opponent.avatarUrl || 'default-avatar.png'
          };
  
          this.webSocketService.sendMessage({
            userId: this.user.id,
            userDeck: this.userDeck,
            opponentDeck: this.opponentDeck,
            winner: this.winner,
            user: userWithAvatar,
            opponent: opponentWithAvatar
          });
  
          this.saveMatch(); // Salva il match qui, dopo aver inviato il messaggio WebSocket
  
          this.cdr.detectChanges();
        }
      },
      (error) => {
        console.error('Error getting votes for opponent deck:', error);
        this.errorMessage = 'Error getting votes for opponent deck';
      }
    );
  }
  
  saveMatch(): void {
    if (this.user && this.opponent && this.winner) {
      const match = {
        user1Id: this.user.id,
        user2Id: this.opponent.id,
        user1Score: this.calculateTotalScore(this.userDeck),
        user2Score: this.calculateTotalScore(this.opponentDeck),
        winnerId: this.winner.id
      };
  
      console.log('Attempting to save match:', match);
  
      this.http.post('http://localhost:8080/api/game/save', match).subscribe(
        () => {
          console.log('Match saved successfully');
        },
        (error) => {
          console.error('Error saving match:', error);
        }
      );
    }
  }

  battle(user1: AuthData, user2: AuthData): void {
    const user1Score = this.calculateTotalScore(this.userDeck);
    const user2Score = this.calculateTotalScore(this.opponentDeck);

    if (user1Score > user2Score) {
      this.winner = user1;
    } else {
      this.winner = user2;
    }
    this.cdr.detectChanges();
  }

  calculateTotalScore(votes: GiocatoriVotiNellePartite[]): number {
    return votes.reduce((total, vote) => {
      const rating = vote.rating !== null ? vote.rating : 0;
      return total + rating;
    }, 0);
  }
  
}
