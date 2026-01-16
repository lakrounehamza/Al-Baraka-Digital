import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../../services/auth';

export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  if (!authService.isAuthenticated()) {
    return router.createUrlTree(['/login']); // Assuming login route is '/login'
  }

  const requiredRole = route.data?.['role'];
  if (requiredRole && !authService.hasRole(requiredRole)) {
    return router.createUrlTree(['/authorized']);
  }

  // Additional logic for specific redirects if needed
  const role = authService.getRole();
  if (role === 'CLIENT' && !state.url.startsWith('/client')) {
    return router.createUrlTree(['/client']);
  }
  if (role === 'AGENT_BANCAIRE' && !state.url.startsWith('/agent')) {
    return router.createUrlTree(['/agent/dashboard']);
  }
  if (role === 'Admin' && !state.url.startsWith('/admin')) {
    return router.createUrlTree(['/admin/dashboard']);
  }

  return true;
};
