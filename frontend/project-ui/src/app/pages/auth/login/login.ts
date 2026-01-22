import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);
  loginData = { email: '', password: '' };

  onLogin() {
    this.authService.login(this.loginData).subscribe({
      next: () => this.router.navigate(['/products']),
      error: (err) => alert('Falha no login: ' + err.error.message)
    });
  }
}