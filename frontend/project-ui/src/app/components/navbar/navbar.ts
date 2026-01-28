import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http'; // Importação adicionada
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
  private http = inject(HttpClient); // Injeção adicionada
  
  isLoggedIn = false;
  showDropdown = false;
  showCategoryMenu = false;
  categories: any[] = [];
  cartCount = 0;
  isAdmin = false;

  ngOnInit(): void {
    // Carrega as categorias dinamicamente do backend
    this.http.get<any[]>('http://localhost:8080/categories').subscribe(data => {
      this.categories = data;
    });

    this.productService.cartCount$.subscribe(count => {
      this.cartCount = count;
    });
    
    this.authService.isLoggedIn$.subscribe(status => {
      this.isLoggedIn = status;
      this.isAdmin = status ? this.authService.isAdmin() : false;
    });
  }

  // Alterna a visibilidade do menu de departamentos
  toggleCategoryMenu(): void {
    this.showCategoryMenu = !this.showCategoryMenu;
    if (this.showCategoryMenu) {
      this.showDropdown = false;
    }
  }

  // Filtrar produtos pela categoria selecionada
  filterByCategory(categoryName: string): void {
    this.productService.updateSearchTerm(categoryName);
    this.showCategoryMenu = false;
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
    if (this.showDropdown) {
      this.showCategoryMenu = false;
    }
  }

  logout(): void {
    this.showDropdown = false;
    this.authService.logout();
  }

  onClear() {
    this.productService.updateSearchTerm('');
  }

  goToHome(): void {
    this.productService.updateSearchTerm('');
    this.showCategoryMenu = false;
    this.showDropdown = false;
  }
}