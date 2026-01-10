import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';


interface RegisterResponse {
  id: string;
  username: string;
  email: string;
  UserRole: string;
  active: boolean;
  created_at: string;
}

@Injectable({
  providedIn: 'root',
})
export class RegisterService {
  username = '';
  password = '';
  email = '';

  constructor(private http: HttpClient) {
  }

  register(username: string, password: string, email: string): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>("http://localhost:8080/api/auth/signup", {username, password, email});
  }
}

