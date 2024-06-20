import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { MatchService } from 'src/app/service/match.service';
import { AuthData } from 'src/app/interface/auth-data.interface';

@Component({
  selector: 'app-replay',
  templateUrl: './replay.component.html',
  styleUrls: ['./replay.component.scss']
})
export class ReplayComponent implements OnInit {
  user: AuthData | null = null;
  matches: any[] = [];

  constructor(
    private authService: AuthService,
    private matchService: MatchService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getStoredUser();
    if (this.user) {
      this.loadUserMatches(this.user.id);
    }
  }

  loadUserMatches(userId: number): void {
    this.matchService.getMatchesByUser(userId).subscribe(
      (matches) => {
        this.matches = matches;
      },
      (error) => {
        console.error('Error loading user matches:', error);
      }
    );
  }
}
