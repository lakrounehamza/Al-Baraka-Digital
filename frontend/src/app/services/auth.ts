import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode'; // You'll need to install jwt-decode

interface DecodedToken {
  exp: number;
  role: string;
  // Add other claims as needed
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private router: Router) {}

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  isTokenExpired(): boolean {
    const token = this.getToken();
    if (!token) return true;
    try {
      const decoded: DecodedToken = jwtDecode(token);
      const currentTime = Date.now() / 1000;
      return decoded.exp < currentTime;
    } catch (error) {
      return true;
    }
  }

  isAuthenticated(): boolean {
    return !!this.getToken() && !this.isTokenExpired();
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    this.router.navigate(['']);
  }

  hasRole(requiredRole: string): boolean {
    const role = this.getRole();
    return role === requiredRole;
  }
}