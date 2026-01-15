import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  loginData = { email: '', password: '' };

  constructor(private router: Router) {}

  onLogin() {
    console.log('Tentando logar com:', this.loginData);
    // Aqui chamaremos o backend
    if (this.loginData.email && this.loginData.password) {
      this.router.navigate(['/products']); // Vai para a SPA de produtos
    }
  }
}