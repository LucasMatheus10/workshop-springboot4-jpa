import { Component, OnInit, inject } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.model';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css'
})
export class ProductListComponent implements OnInit {
  private productService = inject(ProductService);
  private router = inject(Router);
  products: Product[] = [];

  ngOnInit(): void {
    this.productService.findAll().subscribe({
      next: (data) => this.products = data,
      error: (err) => console.error('Erro ao buscar produtos', err)
    });
  }

  // Lógica de seleção que exige login
  selectProduct(product: Product): void {
    const token = localStorage.getItem('token'); // Verificação simples de login
    if (!token) {
      alert('Por favor, faça login para selecionar este produto.');
      this.router.navigate(['/login']);
    } else {
      console.log('Produto selecionado:', product);
    }
  }
}