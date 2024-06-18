import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Giocatore } from '../interface/giocatore.interface';
import { AuthService } from '../auth/auth.service';
import { AuthData } from '../interface/auth-data.interface';

@Injectable({
  providedIn: 'root'
})
export class GiocatoreService {
  private apiUrl = 'http://localhost:8080/api/giocatori';
  private apiUrl2 = 'http://localhost:8080/api';


  constructor(private http: HttpClient, private authService: AuthService) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  getGiocatori(page: number, size: number): Observable<{ content: Giocatore[], totalPages: number }> {
    return this.http.get<{ content: Giocatore[], totalPages: number }>(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  addGiocatoreToCarrello(userId: number, giocatoreId: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(`${this.apiUrl2}/users/${userId}/carrello/${giocatoreId}`, {}, { headers });
  }


  removeFromCarrello(userId: number, giocatoreId: number): Observable<AuthData> {
    const headers = this.getAuthHeaders();
    return this.http.delete<AuthData>(`${this.apiUrl2}/users/${userId}/carrello/${giocatoreId}`, { headers });
  }

  addGiocatoriToUser(userId: number, giocatoriIds: number[]): Observable<AuthData> {
    const headers = this.getAuthHeaders();
    return this.http.post<AuthData>(`${this.apiUrl2}/users/${userId}/giocatori`, giocatoriIds, { headers });
  }

  addToDeck(userId: number, giocatoreId: number): Observable<AuthData> {
    const headers = this.getAuthHeaders();
    return this.http.post<AuthData>(`${this.apiUrl2}/users/${userId}/deck/add`, giocatoreId, { headers });
  }

  removeFromDeck(userId: number, giocatoreId: number): Observable<AuthData> {
    const headers = this.getAuthHeaders();
    return this.http.post<AuthData>(`${this.apiUrl2}/users/${userId}/deck/remove`, giocatoreId, { headers });
  }

}