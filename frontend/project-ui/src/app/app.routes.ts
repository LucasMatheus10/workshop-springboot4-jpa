import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'products', pathMatch: 'full' },
  
  { 
    path: 'products', 
    loadComponent: () => import('./components/product-list/product-list').then(m => m.ProductList) 
  },

  { 
    path: 'login', 
    loadComponent: () => import('./components/login/login').then(m => m.Login) 
  },
  
  { 
    path: 'register', 
    loadComponent: () => import('./components/register/register').then(m => m.Register) 
  },
  
  { 
    path: 'payment', 
    loadComponent: () => import('./components/payment/payment').then(m => m.Payment) 
  },
  
  { 
    path: 'admin', 
    loadComponent: () => import('./components/admin/dashboard/dashboard').then(m => m.Dashboard) 
  },
  { 
    path: 'admin/categories', 
    loadComponent: () => import('./components/admin/category-manager/category-manager').then(m => m.CategoryManager) 
  },

  { path: '**', redirectTo: 'products' }
];