import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { Product } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private http = inject(HttpClient);
  private readonly API = 'http://localhost:8080/products';

  // Guarda o termo de busca atual
  private searchTermSubject = new BehaviorSubject<string>('');
  
  searchTerm$ = this.searchTermSubject.asObservable();

  findAll(): Observable<Product[]> {
    return this.http.get<Product[]>(this.API);
  }

  // Atualiza o termo (chamado pela Navbar)
  updateSearchTerm(term: string): void {
    this.searchTermSubject.next(term);
  }
}