import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interface/user.interface';
import { Observable } from 'rxjs';
import { AuthData } from '../interface/auth-data.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';



  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  getRandomUser(currentUserId: number): Observable<AuthData> {
    const params = new HttpParams().set('currentUserId', currentUserId.toString());
    return this.http.get<AuthData>(`${this.apiUrl}/random`, { params });
  }
}
