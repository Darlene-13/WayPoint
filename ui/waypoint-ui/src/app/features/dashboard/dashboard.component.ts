import { Component, DOCUMENT, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../core/services/dashboard.service';
import { ReminderService } from '../../core/services/reminder.service';
import { DashboardSummary } from '../../core/models/dashboard.model';
import { ApplicationStage, JobApplication } from '../../core/models/job-application.model';
import { JobApplicationService } from '../../core/services/job-application.service';
import { Reminder } from '../../core/models/reminder.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  sidebarCollapsed = false;
  summary: DashboardSummary | null = null;
  followUpsToday: Reminder[] = [];
  applications: JobApplication[] = [];
  importMessage = '';
  isLightMode = false;
  private readonly document = inject(DOCUMENT);

  constructor(
    private readonly dashboardService: DashboardService,
    private readonly reminderService: ReminderService,
    private readonly jobApplicationService: JobApplicationService
  ) {}

  toggleSidebar(): void {
    this.sidebarCollapsed = !this.sidebarCollapsed;
  }

  ngOnInit(): void {
    this.restoreTheme();
    this.dashboardService.getSummary().subscribe({
      next: (summary) => (this.summary = summary),
      error: () => (this.summary = null)
    });
    this.reminderService.dueToday().subscribe({
      next: (reminders) => (this.followUpsToday = reminders),
      error: () => (this.followUpsToday = [])
    });
    this.jobApplicationService.list().subscribe({
      next: (applications) => (this.applications = applications),
      error: () => (this.applications = [])
    });
  }

  toggleTheme(): void {
    this.isLightMode = !this.isLightMode;
    this.applyTheme();

    if (typeof localStorage !== 'undefined') {
      localStorage.setItem('waypoint-theme', this.isLightMode ? 'light' : 'dark');
    }
  }

  private restoreTheme(): void {
    if (typeof localStorage !== 'undefined') {
      const savedTheme = localStorage.getItem('waypoint-theme');
      this.isLightMode = savedTheme
        ? savedTheme === 'light'
        : typeof matchMedia !== 'undefined' && matchMedia('(prefers-color-scheme: light)').matches;
    }

    this.applyTheme();
  }

  private applyTheme(): void {
    // setAttribute works in browsers, SSR, and the lightweight DOM used by Angular's dev server.
    this.document.documentElement?.setAttribute('data-theme', this.isLightMode ? 'light' : 'dark');
  }

  exportApplications(): void {
    const columns = ['Company', 'Position', 'Stage', 'Location', 'Work mode', 'Date applied', 'Job URL', 'Notes'];
    const rows = this.applications.map((application) => [
      application.companyName, application.position, application.currentStage, application.location ?? '',
      application.workMode ?? '', application.dateApplied, application.jobUrl ?? '', application.notes ?? ''
    ]);
    const csv = [columns, ...rows].map((row) => row.map((value) => `"${String(value).replaceAll('"', '""')}"`).join(',')).join('\n');
    const link = this.document.createElement('a');
    link.href = URL.createObjectURL(new Blob([`\ufeff${csv}`], { type: 'text/csv;charset=utf-8' }));
    link.download = `waypoint-applications-${new Date().toISOString().slice(0, 10)}.csv`;
    link.click();
    URL.revokeObjectURL(link.href);
  }

  importApplications(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = () => {
      this.importMessage = `${file.name} is ready to review. Import syncing will be connected to your API next.`;
      input.value = '';
    };
    reader.readAsText(file);
  }

  stageCount(stage: ApplicationStage): number {
    return this.summary?.countsByStage?.[stage] ?? 0;
  }

  closedOutCount(): number {
    return this.stageCount('REJECTED') + this.stageCount('WITHDRAWN') + this.stageCount('GHOSTED');
  }

  reminderLabel(reminder: Reminder): string {
    switch (reminder.reminderType) {
      case 'FOLLOW_UP':
        return 'Follow-up due';
      case 'OA_EXPIRY':
        return 'OA expires today';
      case 'INTERVIEW':
        return 'Interview today';
      default:
        return reminder.reminderType;
    }
  }
}
