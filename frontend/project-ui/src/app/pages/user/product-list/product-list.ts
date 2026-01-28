import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product.model';

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

  // Lista original vinda da API
  allProducts: Product[] = [];
  
  // Lista filtrada que será exibida no HTML
  filteredProducts: Product[] = [];

  ngOnInit(): void {
    // Carrega todos os produtos ao iniciar o componente
    this.productService.findAll().subscribe({
      next: (data) => {
        this.allProducts = data;
        this.filteredProducts = data;
      },
      error: (err) => console.error('Erro ao buscar produtos', err)
    });

    // Adiciona delay na busca
    this.productService.searchTerm$
      .pipe(
        debounceTime(400),        // Aguarda 400ms após a última tecla digitada
        distinctUntilChanged()    // Só executa se o termo for diferente do anterior
      )
      .subscribe(term => {
        this.filterProducts(term);
      });
  }

  // Método responsável por aplicar o filtro na lista
  filterProducts(term: string): void {
    if (!term.trim()) {
      // Se a pesquisa estiver vazia, mostra todos os produtos
      this.filteredProducts = this.allProducts;
    } else {
      const lowerTerm = term.toLowerCase();
      this.filteredProducts = this.allProducts.filter(product =>
        product.name.toLowerCase().includes(lowerTerm) ||
        product.description.toLowerCase().includes(lowerTerm)
      );
    }
  }

  // Lógica para selecionar produto (exige login)
  selectProduct(product: Product): void {
    const token = localStorage.getItem('token');
    if (!token) {
      alert('Por favor, faça login para selecionar este produto.');
      this.router.navigate(['/login']);
      return;
    }

    // Recupera o carrinho atual ou cria um novo array
    const cart: Product[] = JSON.parse(localStorage.getItem('cart') || '[]');
    
    // Adiciona o novo produto ao carrinho
    cart.push(product);
    
    // Salva de volta no localStorage
    localStorage.setItem('cart', JSON.stringify(cart));

    this.productService.updateCartCount();
    
    alert(`${product.name} adicionado ao carrinho!`);
  }
}