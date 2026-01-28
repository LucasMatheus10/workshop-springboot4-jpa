import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class NavbarComponent implements OnInit {
  private productService = inject(ProductService);
  private authService = inject(AuthService);
  
  isLoggedIn = false;
  showDropdown = false;
  cartCount = 0;

  ngOnInit(): void {
    this.productService.cartCount$.subscribe(count => {
      this.cartCount = count;
    });
    
    this.authService.isLoggedIn$.subscribe(status => {
      this.isLoggedIn = status;
    });
  }

  updateCartCount() {
    const cart = JSON.parse(localStorage.getItem('cart') || '[]');
    this.cartCount = cart.length;
  }

  onSearch(event: any): void {
    const term = event.target.value;
    this.productService.updateSearchTerm(term);
  }

  toggleDropdown(): void {
    this.showDropdown = !this.showDropdown;
  }

  logout(): void {
    this.showDropdown = false;
    this.authService.logout(); // Centraliza a limpeza do token e redirecionamento
  }

  onClear() {
    this.productService.updateSearchTerm('');
  }
}