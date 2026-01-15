import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  
  // Tela de Login
  { 
    path: 'login', 
    loadComponent: () => import('./components/login/login').then(m => m.Login) 
  },
  
  // Tela de Registro (Adicionei aqui para você)
  { 
    path: 'register', 
    loadComponent: () => import('./components/register/register').then(m => m.Register) 
  },
  
  // Rotas de Usuário Comum
  { 
    path: 'products', 
    loadComponent: () => import('./components/product-list/product-list').then(m => m.ProductList) 
  },
  { 
    path: 'payment', 
    loadComponent: () => import('./components/payment/payment').then(m => m.Payment) 
  },
  
  // Rotas de Administrador
  { 
    path: 'admin', 
    loadComponent: () => import('./components/admin/dashboard/dashboard').then(m => m.Dashboard) 
  },
  { 
    path: 'admin/categories', 
    loadComponent: () => import('./components/admin/category-manager/category-manager').then(m => m.CategoryManager) 
  }
];