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
    if (this.loginData.email && this.loginData.password) {
      this.authService.login(this.loginData).subscribe({
        next: (res) => {
          // Armazena o e-mail para identificação posterior do ID do usuário
          localStorage.setItem('userEmail', this.loginData.email);
          if (this.authService.isAdmin()) {
            this.router.navigate(['/admin/dashboard']);
          } else {
            this.router.navigate(['/products']);
          }
        },
        error: (err) => {
          // Se o erro for 401 ou 403 (Unauthorized/Forbidden)
          if (err.status === 401 || err.status === 403) {
            alert('E-mail ou senha incorretos. Verifique seus dados.');
          } else if (err.error && err.error.message) {
            // Se o backend enviou uma mensagem específica no StandardError
            alert(err.error.message);
          } else {
            alert('Ocorreu um erro ao tentar logar. Tente novamente mais tarde.');
          }
        }
      });
    }
  }
}