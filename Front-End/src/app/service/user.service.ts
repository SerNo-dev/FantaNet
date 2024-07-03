import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interface/user.interface';
import { Observable } from 'rxjs';
import { AuthData } from '../interface/auth-data.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.apiURL}/api/users`;



  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

 

  getRandomUserWithFullDeck(userId: number): Observable<AuthData> {
    return this.http.get<AuthData>(`${this.apiUrl}/randomWithFullDeck?userId=${userId}`);
  }
}
