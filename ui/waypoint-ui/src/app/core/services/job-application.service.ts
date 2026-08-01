import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  ApplicationStage,
  JobApplication,
  JobApplicationRequest,
  StageChangeRequest
} from '../models/job-application.model';

@Injectable({ providedIn: 'root' })
export class JobApplicationService {
  private readonly baseUrl = `${environment.apiBaseUrl}/applications`;

  constructor(private readonly http: HttpClient) {}

  list(stage?: ApplicationStage): Observable<JobApplication[]> {
    let params = new HttpParams();
    if (stage) params = params.set('stage', stage);
    return this.http.get<JobApplication[]>(this.baseUrl, { params });
  }

  getById(id: string): Observable<JobApplication> {
    return this.http.get<JobApplication>(`${this.baseUrl}/${id}`);
  }

  create(payload: JobApplicationRequest): Observable<JobApplication> {
    return this.http.post<JobApplication>(this.baseUrl, payload);
  }

  changeStage(id: string, payload: StageChangeRequest): Observable<JobApplication> {
    return this.http.patch<JobApplication>(`${this.baseUrl}/${id}/stage`, payload);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
