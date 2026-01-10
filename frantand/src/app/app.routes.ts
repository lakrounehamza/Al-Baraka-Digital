import {Routes} from '@angular/router';

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
    path:'client',
    loadComponent:()=>
      import('./components/clinet/profile/profile').then(m=>m.Profile)
  },
  {
    path:'**',
    loadComponent: () =>
      import('./components/auth/login/login').then(m => m.Login)
  }
];
