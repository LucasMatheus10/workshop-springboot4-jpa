import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private http = inject(HttpClient);
  private readonly API = 'http://localhost:8080/products';

  findAll(): Observable<Product[]> {
    return this.http.get<Product[]>(this.API);
  }
}