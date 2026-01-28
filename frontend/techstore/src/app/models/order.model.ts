import { Product } from './product.model';

// Espelha o UserDTO.java
export interface UserDTO {
  id: number;
  name: string;
  email: string;
  phone: string;
}

// Espelha o OrderItemDTO.java
export interface OrderItemDTO {
  productId: number;
  productName: string;
  quantity: number;
  price: number;
  subtotal: number;
}

// Espelha o OrderDTO.java
export interface OrderDTO {
  id: number;
  moment: string; // ISO Date string
  orderStatus: 'WAITING_PAYMENT' | 'PAID' | 'SHIPPED' | 'DELIVERED' | 'CANCELED';
  client: UserDTO;
  total: number;
  items: OrderItemDTO[];
}

// Espelha o OrderInsertDTO.java
export interface OrderInsertDTO {
  clientId: number;
  items: {
    productId: number;
    quantity: number;
  }[];
}