import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class RegisterComponent {
  userData = {
    name: '',
    email: '',
    phone: '',
    password: ''
  };

  constructor(private router: Router) {}

  onRegister() {
    console.log('Enviando para o backend:', this.userData);
    // Integração futura com o AuthService (POST /users)
    // Após o sucesso:
    alert('Usuário cadastrado com sucesso!');
    this.router.navigate(['/login']);
  }
}