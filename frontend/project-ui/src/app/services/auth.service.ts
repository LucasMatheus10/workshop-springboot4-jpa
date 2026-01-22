import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private router = inject(Router);
  
  // Estado reativo do login
  private loggedIn = new BehaviorSubject<boolean>(!!localStorage.getItem('token'));
  isLoggedIn$ = this.loggedIn.asObservable();

  login(token: string) {
    localStorage.setItem('token', token);
    this.loggedIn.next(true); // Avisa todos que logou
    this.router.navigate(['/products']);
  }

  logout() {
    localStorage.removeItem('token');
    this.loggedIn.next(false); // Avisa todos que saiu
    this.router.navigate(['/login']);
  }

  checkToken(): boolean {
    return !!localStorage.getItem('token');
  }
}