import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AuthData } from 'src/app/interface/auth-data.interface';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  user: AuthData | null = null;

  constructor(private authSrv: AuthService) {}
  ngOnInit(): void {
    this.authSrv.user$.subscribe((user) => {
      this.user = user;
      console.log('User updated in NavbarComponent:', this.user);
    });
  
    // Carica l'utente dal localStorage al momento dell'inizializzazione
    const storedUser = this.authSrv.getStoredUser();
    if (storedUser) {
      this.user = storedUser;
      console.log('Loaded user from localStorage in NavbarComponent:', this.user);
      console.log('Token from stored user:', this.authSrv.getToken());
    }
  }
  logout() {
    this.authSrv.logout();
  }
}
