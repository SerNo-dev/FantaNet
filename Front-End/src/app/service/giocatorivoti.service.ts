// giocatori-voti.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GiocatoriVotiNellePartite } from '../interface/giocatori-voti-nelle-partite.interface'; // Importa l'interfaccia GiocatoriVotiNellePartite
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GiocatoriVotiService {
  private apiUrl = `${environment.apiURL}/api/voti`;

  constructor(private http: HttpClient) {}
  getVotiByUserDeck(userId: number): Observable<GiocatoriVotiNellePartite[]> {
    return this.http.get<GiocatoriVotiNellePartite[]>(`${this.apiUrl}/user/${userId}/deck`);
  }

}
