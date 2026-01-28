import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-category-manager',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './category-manager.html',
  styleUrl: './category-manager.css'
})
export class CategoryManager {
  private http = inject(HttpClient);
  categoryName = '';

  save() {
    if (!this.categoryName.trim()) return;

    this.http.post('http://localhost:8080/categories', { name: this.categoryName }).subscribe({
      next: () => {
        alert('Categoria criada com sucesso!');
        this.categoryName = '';
      },
      error: (err) => alert('Erro ao criar categoria: ' + (err.error.message || 'Erro desconhecido'))
    });
  }
}