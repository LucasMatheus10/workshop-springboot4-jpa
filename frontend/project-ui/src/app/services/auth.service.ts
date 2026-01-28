import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, tap } from 'rxjs';
import { ProductService } from './product.service';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private readonly AUTH_API = 'http://localhost:8080/auth/login';
  private productService = inject(ProductService);

  private loggedIn = new BehaviorSubject<boolean>(!!localStorage.getItem('token'));
  isLoggedIn$ = this.loggedIn.asObservable();

  isAdmin(): boolean {
    const token = localStorage.getItem('token');
    if (!token) return false;

    try {
      // O token JWT é composto por 3 partes separadas por pontos. 
      // A segunda parte [1] contém os dados (payload).
      const payload = JSON.parse(atob(token.split('.')[1]));
      
      // Verifica se 'ROLE_ADMIN' está presente na lista de roles do token
      return payload.roles && payload.roles.includes('ROLE_ADMIN');
    } catch (error) {
      console.error('Erro ao decodificar token:', error);
      return false;
    }
  }

  login(credentials: any) {
    return this.http.post<{token: string}>(this.AUTH_API, credentials).pipe(
      tap(res => {
        localStorage.setItem('token', res.token);
        this.loggedIn.next(true);
      })
    );
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('userEmail');
    localStorage.removeItem('cart');
    this.productService.updateCartCount();
    this.loggedIn.next(false);
  }
}