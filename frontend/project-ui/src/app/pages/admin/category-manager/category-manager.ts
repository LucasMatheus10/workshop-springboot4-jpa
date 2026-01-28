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
  categories: any[] = [];
  categoryName = '';
  editingId: number | null = null; // Controla se estamos editando ou criando

  ngOnInit() {
    this.loadCategories();
  }

  loadCategories() {
    this.http.get<any[]>('http://localhost:8080/categories').subscribe(data => this.categories = data);
  }

  save() {
    if (!this.categoryName.trim()) return;

    const url = 'http://localhost:8080/categories';
    const request = this.editingId 
      ? this.http.put(`${url}/${this.editingId}`, { name: this.categoryName })
      : this.http.post(url, { name: this.categoryName });

    request.subscribe({
      next: () => {
        alert(this.editingId ? 'Categoria atualizada!' : 'Categoria criada!');
        this.cancelEdit();
        this.loadCategories();
      },
      error: (err) => alert('Erro: ' + (err.error.message || 'Falha na operação'))
    });
  }

  editCategory(cat: any) {
    this.categoryName = cat.name;
    this.editingId = cat.id;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  deleteCategory(id: number) {
    if (confirm('Deseja excluir esta categoria? Isso pode afetar produtos vinculados.')) {
      this.http.delete(`http://localhost:8080/categories/${id}`).subscribe({
        next: () => {
          alert('Categoria removida!');
          this.loadCategories();
        },
        error: () => alert('Erro: Esta categoria pode estar em uso por algum produto.')
      });
    }
  }

  cancelEdit() {
    this.categoryName = '';
    this.editingId = null;
  }
}