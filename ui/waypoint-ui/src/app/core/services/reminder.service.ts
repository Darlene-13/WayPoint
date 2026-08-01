import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Reminder } from '../models/reminder.model';

@Injectable({ providedIn: 'root' })
export class ReminderService {
  private readonly baseUrl = `${environment.apiBaseUrl}/reminders`;

  constructor(private readonly http: HttpClient) {}

  dueToday(): Observable<Reminder[]> {
    return this.http.get<Reminder[]>(`${this.baseUrl}/due-today`);
  }

  markComplete(id: string): Observable<Reminder> {
    return this.http.patch<Reminder>(`${this.baseUrl}/${id}/complete`, {});
  }
}
