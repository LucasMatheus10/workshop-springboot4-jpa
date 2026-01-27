import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class RegisterComponent {
  private http = inject(HttpClient);
  private router = inject(Router);

  userData = { name: '', email: '', phone: '', password: '' };

  onRegister() {
    this.http.post('http://localhost:8080/users', this.userData).subscribe({
      next: () => {
        alert('Usuário cadastrado com sucesso!');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        // Se o backend retornou um erro de validação ou de banco
        if (err.error && err.error.message) {
          alert(err.error.message); // Exibe "Este e-mail já está cadastrado no sistema."
        } else {
          alert('Erro ao realizar cadastro. Tente novamente.');
        }
      }
    });
  }
}