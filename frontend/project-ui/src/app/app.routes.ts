import { Routes } from '@angular/router';
import { adminGuard } from './guards/admin.guard'; // Importe o guard que criamos

export const routes: Routes = [
  { path: '', redirectTo: 'products', pathMatch: 'full' },
  
  // Rotas de Usuário Comum
  { 
    path: 'products', 
    loadComponent: () => import('./pages/user/product-list/product-list').then(m => m.ProductListComponent) 
  },
  { 
    path: 'login', 
    loadComponent: () => import('./pages/auth/login/login').then(m => m.LoginComponent) 
  },
  { 
    path: 'forgot-password', 
    loadComponent: () => import('./pages/auth/forgot-password/forgot-password').then(m => m.ForgotPasswordComponent) 
  },
  { 
    path: 'register', 
    loadComponent: () => import('./pages/auth/register/register').then(m => m.RegisterComponent) 
  },
  { 
    path: 'payment', 
    loadComponent: () => import('./pages/user/payment/payment').then(m => m.PaymentComponent) 
  },
  
  // Rotas Administrativas Protegidas
  { 
    path: 'admin', 
    canActivate: [adminGuard], // Impede o acesso de não-admins
    children: [
      { 
        path: 'dashboard', 
        loadComponent: () => import('./pages/admin/dashboard/dashboard').then(m => m.Dashboard) 
      },
      { 
        path: 'products', 
        loadComponent: () => import('./pages/admin/product-manager/product-manager').then(m => m.ProductManager) 
      },
      { 
        path: 'categories', 
        loadComponent: () => import('./pages/admin/category-manager/category-manager').then(m => m.CategoryManager) 
      },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' } // Redireciona /admin para o dashboard
    ]
  },

  { path: '**', redirectTo: 'products' }
];