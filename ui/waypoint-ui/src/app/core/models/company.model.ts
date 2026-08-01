export interface Company {
  id: string;
  name: string;
  website?: string;
  industry?: string;
  notes?: string;
  createdAt: string;
  updatedAt: string;
}

export interface CompanyRequest {
  name: string;
  website?: string;
  industry?: string;
  notes?: string;
}
