import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { AuthData } from 'src/app/interface/auth-data.interface';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit  {
  user!: AuthData | null;
  selectedFile: File | null = null;
  avatarUpdated: boolean = false; 

  constructor(private authSrv: AuthService, private http: HttpClient, private router: Router, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.authSrv.user$.subscribe((user) => {
      this.user = user;
      console.log('User ID:', this.user?.id);  
    });
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
    console.log('File selected:', this.selectedFile);
  }

  uploadAvatar(): void {
    if (!this.selectedFile || !this.user?.id) {
      console.log('No file selected or user ID missing');
      return;
    }

    console.log('Upload Avatar function called');
    const formData = new FormData();
    formData.append('avatar', this.selectedFile);
    console.log('FormData prepared:', formData);

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.user.accessToken}`
    });

    console.log('Headers prepared:', headers);

    this.http.patch<{ avatarUrl: string }>(`${environment.apiURL}/api/users/${this.user.id}/avatar`, formData, { headers })
      .subscribe(response => {
        console.log('HTTP PATCH response:', response);
        if (this.user) {
          this.user.avatar = response.avatarUrl;
          this.authSrv.updateUser(this.user); 
          console.log('Avatar updated:', response.avatarUrl);

          this.avatarUpdated = true;
          console.log('avatarUpdated set to true'); 
          this.cdr.detectChanges(); 
        }
      }, error => {
        console.error('Error uploading avatar:', error); 
      });
  }

}
