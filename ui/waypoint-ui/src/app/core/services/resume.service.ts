import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Resume, ResumeRequest } from '../models/resume.model';

@Injectable({ providedIn: 'root' })
export class ResumeService {
  private readonly baseUrl = `${environment.apiBaseUrl}/resumes`;

  constructor(private readonly http: HttpClient) {}

  list(): Observable<Resume[]> {
    return this.http.get<Resume[]>(this.baseUrl);
  }

  getById(id: string): Observable<Resume> {
    return this.http.get<Resume>(`${this.baseUrl}/${id}`);
  }

  create(payload: ResumeRequest): Observable<Resume> {
    return this.http.post<Resume>(this.baseUrl, payload);
  }
  upload(label: string, targetRole: string, file: File): Observable<Resume> {
    const data = new FormData(); data.append('label', label); if (targetRole) data.append('targetRole', targetRole); data.append('file', file);
    return this.http.post<Resume>(`${this.baseUrl}/upload`, data);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
