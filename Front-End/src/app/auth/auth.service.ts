import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Register } from '../interface/register.interface';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { AuthData } from '../interface/auth-data.interface';
import { Login } from '../interface/login.interface';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiURL = 'http://localhost:8080/auth';
  private apiUserURL = 'http://localhost:8080/api/users';
  private token: string | null = localStorage.getItem('token');
  private authSub = new BehaviorSubject<AuthData | null>(this.getStoredUser());
  user$ = this.authSub.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    const storedUser = this.getStoredUser();
    if (storedUser) {
      this.authSub.next(storedUser);
      this.token = storedUser.accessToken;
    }
  }

  getToken(): string | null {
    if (!this.token) {
      const user = this.getStoredUser();
      this.token = user ? user.accessToken : null;
      if (this.token) {
        localStorage.setItem('token', this.token);
      }
    }
    console.log('Token:', this.token);
    return this.token;
  }

  login(user: Login): Observable<AuthData> {
    return this.http.post<AuthData>(`${this.apiURL}/login`, user).pipe(
      tap((authData: AuthData) => {
        this.authSub.next(authData);
        localStorage.setItem('user', JSON.stringify(authData));
        localStorage.setItem('token', authData.accessToken);
        this.token = authData.accessToken;
        console.log('User logged in:', authData);
        this.router.navigate(['/dashboard/players']);
      }),
      catchError(error => {
        console.error('Login error:', error);
        return throwError(error);
      })
    );
  }

  getStoredUser(): AuthData | null {
    const storedUser = localStorage.getItem('user');
    console.log('Stored User in getStoredUser:', storedUser);
    return storedUser ? JSON.parse(storedUser) : null;
  }
 
  updateUser(user: AuthData) {
    console.log('updateUser() chiamato con:', user);
    
    // Recupera l'utente attuale dal localStorage
    const currentUser = this.getStoredUser();
    if (currentUser) {
      // Mantieni l'avatar se esiste nell'utente attuale
      if (!user.avatar && currentUser.avatar) {
        user.avatar = currentUser.avatar;
      }
    }

    this.authSub.next(user);
    localStorage.setItem('user', JSON.stringify(user));

    // Debugging dettagliato del token
    if (user.accessToken) {
      this.token = user.accessToken;
      localStorage.setItem('token', user.accessToken);
      console.log('Token aggiornato in updateUser:', this.token);
    } else {
      console.error('Token non trovato nell\'utente aggiornato:', user);
    }
  }

  getUserById(id: number): Observable<AuthData> {
    const headers = { 'Authorization': `Bearer ${this.getToken()}` };
    console.log('Get user by ID headers:', headers);
    return this.http.get<AuthData>(`${this.apiUserURL}/${id}`, { headers }).pipe(
      catchError(error => {
        console.error('Get user by ID error:', error);
        return throwError(error);
      })
    );
  }

  logout() {
    this.authSub.next(null);
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    this.token = null;
    this.router.navigate(['/login']);
    console.log('User logged out');
  }

  signUp(user: Register): Observable<string> {
    return this.http
      .post<string>(`${this.apiURL}/register`, user)
      .pipe(catchError(this.errors));
  }

  private errors(err: any) {
    switch (err.error) {
      case 'Email already exists':
        return throwError('utente già presente');
      case 'Incorrect password':
        return throwError('password errata');
      case 'Cannot find user':
        return throwError('Utente non trovato');
      default:
        return throwError('Errore nella chiamata');
    }
  }
}