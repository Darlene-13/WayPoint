import { ApplicationStage } from './job-application.model';

export interface DashboardSummary {
  totalApplications: number;
  countsByStage: Partial<Record<ApplicationStage, number>>;
  responseRatePct: number;
  successRatePct: number;
  followUpsDueToday: number;
}

export interface WeeklyCount {
  weekStarting: string;
  applicationsSent: number;
}

export interface ResumePerformance {
  resumeLabel: string;
  totalSent: number;
  interviews: number;
  interviewRatePct: number;
}
