import { Component } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    const token = this.authService.getToken();
    if (!token) {
      // Gestisci il caso in cui il token non è presente (ad es. reindirizza al login)
      console.log('Token non presente, reindirizza al login');
    } else {
      console.log('Token:', token);
    }
  }
}
