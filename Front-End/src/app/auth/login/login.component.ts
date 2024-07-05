import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  
  constructor(private authSrv: AuthService, private router: Router) {}

  login(form: NgForm) {
    this.showLoader();
    this.authSrv.login(form.value).subscribe({
      next: () => {
        this.router.navigate(['/dashboard/players']).then(() => {
          this.hideLoader();
        });
      },
      error: (error) => {
        console.error(error);
        this.hideLoader();
      }
    });
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
