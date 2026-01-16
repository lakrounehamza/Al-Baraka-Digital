import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OperationService {

  constructor(private http:HttpClient) {
  }
  private readonly API_URL = 'http://localhost:8080/api/client/operations';

  createOperation(
    type: string,
    amount: number,
    accountDestinationId: string
  ): Observable<any> {

    const body = {
      type,
      amount,
      accountDestinationId
    };

    return this.http.post<any>(this.API_URL, body);
  }
}
