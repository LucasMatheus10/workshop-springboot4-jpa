import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './forgot-password.html',
  styleUrl: './forgot-password.css'
})
export class ForgotPasswordComponent {
  private http = inject(HttpClient);
  
  email: string = '';
  newPassword: string = '';
  step: number = 1; // 1: Email, 2: Nova Senha, 3: Sucesso
  userId: number | null = null;

  findUserByEmail() {
    this.http.get<any>(`http://localhost:8080/users/search?email=${this.email}`).subscribe({
      next: (user) => {
        this.userId = user.id;
        this.step = 2;
      },
      error: () => alert('Usuário não encontrado com este e-mail.')
    });
  }

  onResetPassword() {
    if (this.userId) {
      const resetData = { newPassword: this.newPassword };
    
      this.http.put(`http://localhost:8080/users/${this.userId}/password`, resetData).subscribe({
        next: () => this.step = 3,
        error: (err) => {
          // Verifica se o backend enviou uma mensagem de erro específica
          if (err.status === 400 || err.status === 500) {
            alert(err.error.message || 'A nova senha não pode ser igual à atual.');
          } else {
            alert('Erro ao atualizar a senha. Tente novamente mais tarde.');
          }
        }
      });
    }
  }
}