import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Reminder } from '../models/reminder.model';

export interface ReminderStats { total: number; dueToday: number; upcoming: number; overdue: number; completed: number; }

@Injectable({ providedIn: 'root' })
export class ReminderService {
  private readonly baseUrl = `${environment.apiBaseUrl}/reminders`;

  constructor(private readonly http: HttpClient) {}

  dueToday(): Observable<Reminder[]> {
    return this.http.get<Reminder[]>(`${this.baseUrl}/due-today`);
  }
  all(): Observable<Reminder[]> { return this.http.get<Reminder[]>(this.baseUrl); }
  upcoming(): Observable<Reminder[]> { return this.http.get<Reminder[]>(`${this.baseUrl}/upcoming`); }
  overdue(): Observable<Reminder[]> { return this.http.get<Reminder[]>(`${this.baseUrl}/overdue`); }
  stats(): Observable<ReminderStats> { return this.http.get<ReminderStats>(`${this.baseUrl}/stats`); }

  markComplete(id: string): Observable<Reminder> {
    return this.http.patch<Reminder>(`${this.baseUrl}/${id}/complete`, {});
  }
}
