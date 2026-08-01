export interface Resume {
  id: string;
  label: string;
  targetRole?: string;
  fileUrl?: string;
  createdAt: string;
}

export interface ResumeRequest {
  label: string;
  targetRole?: string;
  fileUrl?: string;
}
