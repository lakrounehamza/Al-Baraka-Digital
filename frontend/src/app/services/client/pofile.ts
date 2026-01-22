import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

interface OperationResponse {
  id: string;
  type: string;
  amount: string,
  status: string;
  created_at: string;
  validated_at: string;
  accountDestination: AccountDestenation;
}

interface AccountResponse {
  onwer: UserResponse;
  numer: String;
  balance: String;
  operation: OperationResponse[];
}

interface AccountDestenation {
  numer: String;
  username: String;
}

interface UserResponse {
  id: string;
  username: string;
  email: string;
  role: string;
  active: boolean;
  created_at: string;
}

@Injectable({
  providedIn: 'root',
})
export class PofileService {
  constructor(private http: HttpClient) {
  }

  getAccount(): Observable<AccountResponse> {
    return this.http.get<AccountResponse>("http://localhost:8080/api/client");
  }

}
