import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Operations {

  private readonly API_URL = 'http://localhost:8080/api/agent/operations';

  constructor(private http: HttpClient) {}

  // Récupérer les opérations en attente
  getPendingOperations(): Observable<any> {
    return this.http.get<any>(`${this.API_URL}/pending`);
  }

  // Approuver une opération
  approveOperation(operationId: string): Observable<any> {
    return this.http.patch<any>(`${this.API_URL}/${operationId}/approve`, {});
  }

  // Rejeter une opération
  rejectOperation(operationId: string): Observable<any> {
    return this.http.patch<any>(`http://localhost:8080/api/client/operations/${operationId}/reject`, {});
  }
}
