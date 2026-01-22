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
  loginData = { email: '', password: '' };

  constructor(private router: Router) {}

  onLogin() {
    console.log('Tentando logar com:', this.loginData);
    // Aqui chamaremos o backend
    if (this.loginData.email && this.loginData.password) {
      this.authService.login('user-token-fake'); // Simula um token
    }
  }
}