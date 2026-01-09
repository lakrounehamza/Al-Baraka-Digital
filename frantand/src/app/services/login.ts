import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

interface LoginResponse {
  user: {
    id: string,
    username: string,
    email: string,
    role: string,
    active: boolean,
    created_at: string
  };
  token: string;
}

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {
  }

  private url = "http://localhost:8080/api/auth/signin";
  login(username: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.url}`, {username, password});
  }
}
