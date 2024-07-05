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
    this.showLoader();

    const token = this.authService.getToken();
    if (!token) {
    
      console.log('Token non presente, reindirizza al login');
      this.hideLoader();
    } else {
      console.log('Token:', token);

      setTimeout(() => {
        this.hideLoader();
      }, 2000); 
    }
  }

  showLoader(): void {
    const loader = document.getElementById('loader');
    if (loader) {
      loader.style.display = 'flex';
    }
  }

  hideLoader(): void {
    const loader = document.getElementById('loader');
    if (loader) {
      loader.style.display = 'none';
    }
  }
}
