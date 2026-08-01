import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Company, CompanyRequest } from '../models/company.model';

@Injectable({ providedIn: 'root' })
export class CompanyService {
  private readonly baseUrl = `${environment.apiBaseUrl}/companies`;

  constructor(private readonly http: HttpClient) {}

  list(): Observable<Company[]> {
    return this.http.get<Company[]>(this.baseUrl);
  }

  getById(id: string): Observable<Company> {
    return this.http.get<Company>(`${this.baseUrl}/${id}`);
  }

  create(payload: CompanyRequest): Observable<Company> {
    return this.http.post<Company>(this.baseUrl, payload);
  }

  update(id: string, payload: CompanyRequest): Observable<Company> {
    return this.http.put<Company>(`${this.baseUrl}/${id}`, payload);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
