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
  // Objeto espelhando exatamente o que o ProductInsertDTO espera
  product = { 
    name: '', 
    description: '', 
    price: 0, 
    imgUrl: '', 
    categories: [] as any[] 
  };

  ngOnInit() {
    // Busca as categorias cadastradas para o select
    this.http.get<any[]>('http://localhost:8080/categories').subscribe({
      next: (data) => this.categoriesList = data,
      error: (err) => console.error('Erro ao carregar categorias', err)
    });
  }

  isSelected(cat: any): boolean {
    return this.product.categories.some(c => c.id === cat.id);
  }

  // Lógica para adicionar/remover categoria da seleção
  toggleCategory(cat: any) {
    const index = this.product.categories.findIndex(c => c.id === cat.id);
    if (index > -1) {
      this.product.categories.splice(index, 1);
    } else {
      this.product.categories.push({ id: cat.id, name: cat.name });
    }
  }

  save() {
    this.http.post('http://localhost:8080/products', this.product).subscribe({
      next: () => {
        alert('Produto salvo com sucesso!');
        this.product = { name: '', description: '', price: 0, imgUrl: '', categories: [] };
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
}