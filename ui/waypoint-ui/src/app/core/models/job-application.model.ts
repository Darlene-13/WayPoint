export type WorkMode = 'REMOTE' | 'HYBRID' | 'ONSITE';

export type ApplicationStage =
  | 'APPLIED'
  | 'OA'
  | 'INTERVIEW'
  | 'OFFER'
  | 'REJECTED'
  | 'WITHDRAWN'
  | 'GHOSTED';

export interface JobApplication {
  id: string;
  companyId: string;
  companyName: string;
  resumeId?: string;
  resumeLabel?: string;
  position: string;
  location?: string;
  workMode?: WorkMode;
  salaryMin?: number;
  salaryMax?: number;
  salaryCurrency?: string;
  jobUrl?: string;
  dateApplied: string;
  applicationDeadline?: string;
  currentStage: ApplicationStage;
  notes?: string;
  createdAt: string;
  updatedAt: string;
}

export interface JobApplicationRequest {
  companyId: string;
  resumeId?: string;
  position: string;
  location?: string;
  workMode?: WorkMode;
  salaryMin?: number;
  salaryMax?: number;
  salaryCurrency?: string;
  jobUrl?: string;
  dateApplied?: string;
  applicationDeadline?: string;
  notes?: string;
}

export interface StageChangeRequest {
  newStage: ApplicationStage;
  reminderDate?: string;
  notes?: string;
}
