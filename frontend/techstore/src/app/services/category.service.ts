import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CategoryService {
  private http = inject(HttpClient);
  private readonly API = 'http://localhost:8080/categories';

  // Gerenciamento reativo de categorias
  private categoriesSubject = new BehaviorSubject<any[]>([]);
  categories$ = this.categoriesSubject.asObservable();

  constructor() {
    this.refreshCategories(); // Carrega as categorias ao iniciar o serviço
  }

  refreshCategories(): void {
    this.http.get<any[]>(this.API).subscribe({
      next: (data) => this.categoriesSubject.next(data),
      error: (err) => console.error('Erro ao buscar categorias:', err)
    });
  }

  findAll(): Observable<any[]> {
    return this.http.get<any[]>(this.API);
  }
}