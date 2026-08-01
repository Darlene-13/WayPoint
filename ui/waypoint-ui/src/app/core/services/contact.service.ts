import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Contact, ContactRequest } from '../models/contact.model';

@Injectable({ providedIn: 'root' })
export class ContactService {
  private readonly baseUrl = `${environment.apiBaseUrl}/contacts`;

  constructor(private readonly http: HttpClient) {}

  listByCompany(companyId: string): Observable<Contact[]> {
    const params = new HttpParams().set('companyId', companyId);
    return this.http.get<Contact[]>(this.baseUrl, { params });
  }

  getById(id: string): Observable<Contact> {
    return this.http.get<Contact>(`${this.baseUrl}/${id}`);
  }

  create(payload: ContactRequest): Observable<Contact> {
    return this.http.post<Contact>(this.baseUrl, payload);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
