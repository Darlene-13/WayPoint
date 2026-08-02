import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Resume } from '../../core/models/resume.model';
import { ResumeService } from '../../core/services/resume.service';

@Component({selector:'app-resumes',standalone:true,imports:[CommonModule,FormsModule,RouterLink],templateUrl:'./resumes.component.html',styleUrl:'./resumes.component.css'})
export class ResumesComponent implements OnInit {
  resumes: Resume[]=[]; loading=true; showingForm=false; error=''; selectedFile='';
  draft={label:'',targetRole:'',fileUrl:''}; selectedUpload?: File;
  constructor(private readonly resumeService: ResumeService) {}
  ngOnInit(): void { this.load(); }
  onFile(event: Event): void { const file=(event.target as HTMLInputElement).files?.[0]; if(file){this.selectedUpload=file;this.selectedFile=file.name;this.draft.fileUrl=file.name;} }
  add(): void { if(!this.draft.label.trim()) return; const request=this.selectedUpload?this.resumeService.upload(this.draft.label.trim(),this.draft.targetRole,this.selectedUpload):this.resumeService.create({...this.draft,label:this.draft.label.trim()}); request.subscribe({next:r=>{this.resumes=[r,...this.resumes.filter(x=>!x.id.startsWith('demo-'))];this.draft={label:'',targetRole:'',fileUrl:''};this.selectedFile='';this.selectedUpload=undefined;this.showingForm=false;},error:()=>this.error='Could not save this resume.'}); }
  remove(resume: Resume): void { if(resume.id.startsWith('demo-')){this.resumes=this.resumes.filter(r=>r.id!==resume.id);return;} this.resumeService.delete(resume.id).subscribe({next:()=>this.resumes=this.resumes.filter(r=>r.id!==resume.id),error:()=>this.error='Could not delete this resume.'}); }
  private load(): void { this.resumeService.list().subscribe({next:r=>{this.resumes=r.length?r:this.demo();this.loading=false;},error:()=>{this.resumes=this.demo();this.loading=false;}}); }
  private demo(): Resume[]{return [{id:'demo-resume-1',label:'ML Platform — Core Resume',targetRole:'AI / ML Platform Engineer',fileUrl:'ml-platform-resume.pdf',createdAt:''},{id:'demo-resume-2',label:'Backend Systems — Core Resume',targetRole:'Backend Engineer, AI Infrastructure',fileUrl:'backend-ai-resume.pdf',createdAt:''},{id:'demo-resume-3',label:'Applied ML — Research Resume',targetRole:'Applied Machine Learning Engineer',fileUrl:'applied-ml-resume.pdf',createdAt:''}];}
}
