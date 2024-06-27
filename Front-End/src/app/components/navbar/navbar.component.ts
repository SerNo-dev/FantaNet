import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AuthData } from 'src/app/interface/auth-data.interface';
import { AuthService } from 'src/app/auth/auth.service';
import { CartService } from 'src/app/service/cart.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  user: AuthData | null = null;
  cartItemCount: number = 0;

  constructor(private authSrv: AuthService, private cartService: CartService) {}  // Inietta il CartService

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

    // Sottoscrivi agli aggiornamenti del conteggio del carrello
    this.cartService.cartItemCount$.subscribe(count => {
      this.cartItemCount = count;
    });
  }

  logout() {
    this.authSrv.logout();
  }
}
