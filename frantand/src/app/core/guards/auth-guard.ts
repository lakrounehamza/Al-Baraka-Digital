import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');
  console.log(role)
  if (!token) {
    router.navigate(['']);
    return true;
  } else {
    if (state.url === '/client' && role !== 'CLIENT') {
      return router.createUrlTree(['/authorized']);
    }
    if (state.url !== '/client'   && role === 'CLIENT') {
      return router.createUrlTree(['/client']);
    }
    if (state.url === '/agent/dashboard' && role !== 'AGENT_BANCAIRE') {
      return router.createUrlTree(['/authorized']);
    }
    if (state.url !== '/agent/dashboard' && role === 'AGENT_BANCAIRE') {

      return router.createUrlTree(['/agent/dashboard']);
    }

    return true;
  }
}
