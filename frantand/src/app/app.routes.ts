import {Routes} from '@angular/router';
import {authGuard} from './core/guards/auth-guard';

export const routes: Routes = [{
  path: '',
  loadComponent: () =>
    import('./components/auth/login/login').then(m => m.Login)
}
  , {
    path: 'register',
    loadComponent: () =>
      import('./components/auth/register/register').then(m => m.Register)
  },
  {
    path: 'client',
    canActivate:[authGuard],
    loadComponent: () =>
      import('./components/clinet/profile/profile').then(m => m.Profile)
  },
  {
    path: 'agent/dashboard',
    canActivate:[authGuard],
    loadComponent: () =>
      import('./components/agent/dashboard/dashboard').then(d => d.Dashboard)
  },
  {
    path: 'not-found',
    canActivate:[authGuard],
    loadComponent: () =>
      import('./components/error/not-found/not-found').then(d => d.NotFound)
  },
  {
    path: 'authorized',
    canActivate:[authGuard],
    loadComponent: () =>
      import('./components/error/authorized/authorized').then(d => d.Authorized)
  },
  {
    path: '**',
    canActivate:[authGuard],
    loadComponent: () =>
      import('./components/error/not-found/not-found').then(d => d.NotFound)
  }
];
