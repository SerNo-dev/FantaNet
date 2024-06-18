import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interface/user.interface';
import { Observable } from 'rxjs';
import { AuthData } from '../interface/auth-data.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';

  private apiUrl2 = 'http://localhost:8080/api'


  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  getRandomUser(): Observable<AuthData> {
    return this.http.get<AuthData>(`${this.apiUrl2}/users/random`);
  }
}
