import { Component, OnInit, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-product-manager',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './product-manager.html',
  styleUrl: './product-manager.css'
})
export class ProductManager implements OnInit {
  private http = inject(HttpClient);
  
  categoriesList: any[] = []; // Lista vinda do sistema
  products: any[] = [];
  product: any = { id: null, name: '', description: '', price: 0, imgUrl: '', categories: [] };

  ngOnInit() {
    // Busca as categorias e produtos cadastrados
    this.loadCategories();
    this.loadProducts();
  }

  loadCategories() {
    this.http.get<any[]>('http://localhost:8080/categories').subscribe(data => this.categoriesList = data);
  }

  loadProducts() {
    this.http.get<any[]>('http://localhost:8080/products').subscribe(data => this.products = data);
  }

  isSelected(cat: any): boolean {
    return this.product.categories.some((c: any) => c.id === cat.id);
  }

  // Lógica para adicionar/remover categoria da seleção
  toggleCategory(cat: any) {
    const index = this.product.categories.findIndex((c: any) => c.id === cat.id);
    if (index > -1) {
      this.product.categories.splice(index, 1);
    } else {
      this.product.categories.push({ id: cat.id, name: cat.name });
    }
  }

  save() {
      const url = 'http://localhost:8080/products';
      const request = this.product.id 
        ? this.http.put(`${url}/${this.product.id}`, this.product) // Edição
        : this.http.post(url, this.product); // Criação

      request.subscribe({
        next: () => {
          alert(this.product.id ? 'Produto atualizado!' : 'Produto criado!');
          this.resetForm();
          this.loadProducts();
        },
      error: (err) => {
        if (err.status === 422 && err.error.errors) {
          const messages = err.error.errors.map((e: any) => `${e.fieldName}: ${e.message}`).join('\n');
          alert('Erro de Validação:\n' + messages);
        } else {
          alert('Erro: ' + (err.error.message || 'Dados inválidos'));
        }
      }
    });
  }

  editProduct(p: any) {
    // Clona o produto para o formulário (para não editar a linha da tabela em tempo real)
    this.product = { ...p, categories: [...p.categories] };
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  deleteProduct(id: number) {
    if (confirm('Tem certeza que deseja remover este produto?')) {
      this.http.delete(`http://localhost:8080/products/${id}`).subscribe({
        next: () => {
          alert('Produto removido!');
          this.loadProducts();
        },
        error: () => alert('Erro ao remover. O produto pode estar em algum pedido.')
      });
    }
  }

  resetForm() {
    this.product = { id: null, name: '', description: '', price: 0, imgUrl: '', categories: [] };
  }
}