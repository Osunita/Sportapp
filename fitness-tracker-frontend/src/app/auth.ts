import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'  // Disponible en toda la app
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';  // URL del backend (ajusta si puerto diferente)

  constructor(private http: HttpClient) {}

  // Registro: Post al backend, save token si OK
  register(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user).pipe(
      tap((response: any) => this.setToken(response.token))
    );
  }

  // Login: Post, save token
  login(credentials: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials).pipe(
      tap((response: any) => this.setToken(response.token))
    );
  }

  // Save token en localStorage
  private setToken(token: string) {
    localStorage.setItem('token', token);
  }

  // Get token
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Check si logueado
  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  // Logout
  logout() {
    localStorage.removeItem('token');
  }
}
