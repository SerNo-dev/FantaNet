import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Match } from '../interface/match.interface';

@Injectable({
  providedIn: 'root'
})
export class MatchService {
  private apiUrl = `${environment.apiURL}/api/matches`;

  constructor(private http: HttpClient) { }

  getMatchesByUser(userId: number): Observable<Match[]> {
    return this.http.get<Match[]>(`${this.apiUrl}/user/${userId}`);
  }
}
