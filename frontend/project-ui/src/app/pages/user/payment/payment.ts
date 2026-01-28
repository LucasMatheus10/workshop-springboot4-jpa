import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Product } from '../../../models/product.model';
import { OrderDTO, OrderInsertDTO, UserDTO } from '../../../models/order.model';
import { ProductService } from '../../../services/product.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './payment.html',
  styleUrl: './payment.css'
})
export class PaymentComponent implements OnInit {
  private http = inject(HttpClient);
  private router = inject(Router);
  private productService = inject(ProductService);

  selectedProducts: Product[] = []; 
  total = 0;

  ngOnInit() {
    const saved = localStorage.getItem('cart');
    this.loadCart();
  }

  private loadCart() {
    const saved = localStorage.getItem('cart');
    if (saved) {
      const cartData: Product[] = JSON.parse(saved);
      this.selectedProducts = cartData.map(p => ({
        ...p,
        quantity: p.quantity || 1
      }));
      this.calculateTotal();
    }
  }

  calculateTotal() {
    this.total = this.selectedProducts.reduce(
      (acc, item) => acc + (item.price * (item.quantity || 1)), 0
    );
  }

  updateQuantity(product: Product, change: number) {
    const index = this.selectedProducts.findIndex(p => p.id === product.id);
    if (index !== -1) {
      // Garante que quantity nunca seja undefined antes da operação
      const currentQty = this.selectedProducts[index].quantity!;
      const newQty = currentQty + change;

      if (newQty < 1) {
        this.selectedProducts.splice(index, 1);
      } else {
        this.selectedProducts[index].quantity = newQty;
      }

      this.saveAndRefresh();
    }
  }

  clearCart() {
    if (confirm('Deseja realmente esvaziar o carrinho?')) {
      localStorage.removeItem('cart');
      this.selectedProducts = [];
      this.total = 0;
      this.productService.updateCartCount();
    }
  }

  private saveAndRefresh() {
    localStorage.setItem('cart', JSON.stringify(this.selectedProducts));
    this.calculateTotal();
    this.productService.updateCartCount();
  }

  confirmPayment() {
    const userEmail = localStorage.getItem('userEmail'); 

    if (!userEmail) {
      alert('Sessão expirada. Por favor, faça login novamente.');
      this.router.navigate(['/login']);
      return;
    }

    // Busca o usuário para obter o ID real
    this.http.get<UserDTO>(`http://localhost:8080/users/search?email=${userEmail}`).subscribe({
      next: (user) => {
        const orderData: OrderInsertDTO = {
          clientId: user.id,
          // Agora envia a quantidade correta ajustada no front
          items: this.selectedProducts.map(p => ({
            productId: p.id,
            quantity: p.quantity || 1
          }))
        };

        // Cria o pedido no back-end
        this.http.post<OrderDTO>('http://localhost:8080/orders', orderData).subscribe({
          next: (order) => {
            // Atualiza status para PAID através do endpoint de pagamento
            this.http.put<OrderDTO>(`http://localhost:8080/orders/${order.id}/payment`, {}).subscribe({
              next: () => {
                alert('Pagamento aprovado e pedido vinculado à sua conta!');
                localStorage.removeItem('cart');
                this.productService.updateCartCount();
                this.router.navigate(['/']);
              },
              error: (err) => alert(err.error?.message || 'Erro ao processar pagamento.')
            });
          },
          error: (err) => alert(err.error?.message || 'Erro ao criar pedido.')
        });
      },
      error: () => alert('Erro ao identificar usuário logado.')
    });
  }
}