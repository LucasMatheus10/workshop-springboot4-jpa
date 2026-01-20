import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'products', pathMatch: 'full' },
  
  { 
    path: 'products', 
    loadComponent: () => import('./pages/product-list/product-list').then(m => m.ProductListComponent) 
  },

  { 
    path: 'login', 
    loadComponent: () => import('./pages/login/login').then(m => m.LoginComponent) 
  },
  
  { 
    path: 'register', 
    loadComponent: () => import('./pages/register/register').then(m => m.RegisterComponent) 
  },
  
  { 
    path: 'payment', 
    loadComponent: () => import('./pages/payment/payment').then(m => m.PaymentComponent) 
  },
  
  { 
    path: 'admin', 
    loadComponent: () => import('./pages/admin/dashboard/dashboard').then(m => m.Dashboard) 
  },
  { 
    path: 'admin/categories', 
    loadComponent: () => import('./pages/admin/category-manager/category-manager').then(m => m.CategoryManager) 
  },

  { path: '**', redirectTo: 'products' }
];