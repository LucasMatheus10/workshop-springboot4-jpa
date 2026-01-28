import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs'; // Importe BehaviorSubject
import { Product } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private http = inject(HttpClient);
  private readonly API = 'http://localhost:8080/products';

  private searchTermSubject = new BehaviorSubject<string>('');
  searchTerm$ = this.searchTermSubject.asObservable();

  private cartCountSubject = new BehaviorSubject<number>(this.getInitialCount());
  cartCount$ = this.cartCountSubject.asObservable();

  private getInitialCount(): number {
    const cart = JSON.parse(localStorage.getItem('cart') || '[]');
    // Soma as quantidades individuais de cada item para o badge ser preciso
    return cart.reduce((acc: number, item: any) => acc + (item.quantity || 1), 0);
  }

  // Método para disparar a atualização global
  updateCartCount(): void {
    const count = this.getInitialCount();
    this.cartCountSubject.next(count);
  }

  findAll(): Observable<Product[]> {
    return this.http.get<Product[]>(this.API);
  }

  updateSearchTerm(term: string): void {
    this.searchTermSubject.next(term);
  }
}