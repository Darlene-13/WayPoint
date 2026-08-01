export type ContactRole = 'RECRUITER' | 'FOUNDER' | 'HIRING_MANAGER' | 'REFERRAL' | 'OTHER';

export interface Contact {
  id: string;
  companyId: string;
  companyName: string;
  name: string;
  role: ContactRole;
  email?: string;
  linkedinUrl?: string;
  notes?: string;
  createdAt: string;
}

export interface ContactRequest {
  companyId: string;
  name: string;
  role: ContactRole;
  email?: string;
  linkedinUrl?: string;
  notes?: string;
}
