import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  DashboardSummary,
  ResumePerformance,
  WeeklyCount
} from '../models/dashboard.model';

@Injectable({ providedIn: 'root' })
export class DashboardService {
  private readonly baseUrl = `${environment.apiBaseUrl}/dashboard`;

  constructor(private readonly http: HttpClient) {}

  getSummary(): Observable<DashboardSummary> {
    return this.http.get<DashboardSummary>(`${this.baseUrl}/summary`);
  }

  getApplicationsPerWeek(weeksBack = 12): Observable<WeeklyCount[]> {
    const params = new HttpParams().set('weeks', weeksBack);
    return this.http.get<WeeklyCount[]>(`${this.baseUrl}/applications-per-week`, { params });
  }

  getBestPerformingResume(): Observable<ResumePerformance[]> {
    return this.http.get<ResumePerformance[]>(`${this.baseUrl}/best-resume`);
  }
}
