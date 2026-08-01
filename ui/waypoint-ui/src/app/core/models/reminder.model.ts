export type ReminderType = 'FOLLOW_UP' | 'OA_EXPIRY' | 'INTERVIEW' | 'CUSTOM';

export interface Reminder {
  id: string;
  applicationId: string;
  companyName: string;
  position: string;
  reminderType: ReminderType;
  dueDate: string;
  isCompleted: boolean;
  notes?: string;
}
