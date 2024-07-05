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
      try {
          this.authSrv.login(form.value).subscribe();
          this.router.navigate(['/dashboard/players']);
      } catch (error) {
          console.error(error);
      }
  }
}
