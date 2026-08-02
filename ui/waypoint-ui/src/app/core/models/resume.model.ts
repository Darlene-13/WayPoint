export interface Resume {
  id: string;
  label: string;
  targetRole?: string;
  fileUrl?: string;
  fileName?: string;
  contentType?: string;
  createdAt: string;
}

export interface ResumeRequest {
  label: string;
  targetRole?: string;
  fileUrl?: string;
}
