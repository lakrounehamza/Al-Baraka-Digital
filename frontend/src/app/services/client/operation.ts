import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OperationService {

  private readonly API_URL = 'http://localhost:8080/api/client/operations';

  constructor(private http: HttpClient) {}

  createOperation(
    type: string,
    amount: number,
    accountDestinationId?: string
  ): Observable<any> {

    const body: any = {
      type,
      amount
    };

    // Correction : utiliser accountDestination_id avec underscore
    if (type === 'VIREMENT' && accountDestinationId) {
      body.accountDestination_id = accountDestinationId;
    }

    return this.http.post<any>(this.API_URL, body);
  }

  uploadDocument(operationId: string, formData: FormData): Observable<any> {
    return this.http.post<any>(`${this.API_URL}/${operationId}/document`, formData);
  }
}
